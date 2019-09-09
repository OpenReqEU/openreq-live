function bindUIEvent(toObject, methodName) {
    return function(event) { toObject[methodName](event, this); return false; }
}

function showSavedToast() {
    $.toast({
        heading: 'Success',
        text: 'Saved!',
        showHideTransition: 'slide',
        position: 'top-center',
        hideAfter: 1000,
        icon: 'success'
    })
}

function bindRegisteredUIEvents(presenter) {
    // TODO: observer pattern...
    $(".or-requirement-pair-list-item").unbind("click");
    $("#or-show-previous-requirement-pair").unbind("click");
    $("#or-show-next-requirement-pair").unbind("click");
    $(".or-filter-menu > .or-select-menu-item").unbind("click");
    $("#or-dependency-yes-div").unbind("click");
    $("#or-dependency-no-div").unbind("click");
    $("#or-dependency-unsure-div").unbind("click");
    $("#or-dependency-type-chooser").unbind("change");
    $("#or-dependency-direction-chooser").unbind("change");

    $(".or-requirement-pair-list-item").on("click", bindUIEvent(presenter, "showSpecificRequirementPair"));
    $("#or-show-previous-requirement-pair").on("click", bindUIEvent(presenter, "showPreviousRequirementPair"));
    $("#or-show-next-requirement-pair").on("click", bindUIEvent(presenter, "showNextRequirementPair"));
    $(".or-filter-menu > .or-select-menu-item").on("click", bindUIEvent(presenter, "selectMenuItem"));
    $("#or-dependency-yes-div").on("click", bindUIEvent(presenter, "selectDependencyExists"));
    $("#or-dependency-no-div").on("click", bindUIEvent(presenter, "selectDependencyNotExists"));
    $("#or-dependency-unsure-div").on("click", bindUIEvent(presenter, "selectRemoveDependency"));
    $("#or-dependency-type-chooser").on("change", bindUIEvent(presenter, "selectDependencyType"));
    $("#or-dependency-direction-chooser").on("change", bindUIEvent(presenter, "selectDependencyDirection"));

    $("select").material_select();
    $("span.caret").text("");
    $('.tooltipped').tooltip({ delay: 50 });
}

class Timer {

    constructor() {
        this.startTime = null;
        this.sequences = [];
        this.isPaused = false;
        this.idleTimer = null;
        this.idleState = false;
        this.idleWait = 60000;
        this.tolerableIdleInterval = 60000;
        this.lastPauseTime = null;
        this.additionalTime = 0;
    }

    start() {
        console.log("Timer.start()");
        this.startTime = new Date();
        this.sequences = [];
        this.isPaused = false;
        this.lastPauseTime = null;
        this.additionalTime = 0;
        var timer = this;
        $("*").unbind("mousemove keydown scroll");
        $("*").bind("mousemove keydown scroll", function () {
            clearTimeout(timer.idleTimer);
            if (timer.idleState) {
                timer.continue();
                $("#or-overlay").hide();
                console.log("Welcome Back.");
            }

            timer.idleState = false;
            timer.idleTimer = setTimeout(function () {
                console.log("You've been idle for " + timer.idleWait/1000 + " seconds.");
                timer.idleState = true;

                timer.pause();
                console.log("Active for " + getTime() / 1000000 + " seconds.");
                $("#or-overlay").unbind("mousemove keydown scroll click");
                $("#or-overlay").fadeIn();
            }, timer.idleWait);
        });
    }

    pause() {
        console.log("Timer.pause()");
        this.sequences.push({ startTime: this.startTime, endTime: new Date() });
        this.startTime = null;
        this.isPaused = true;
        this.lastPauseTime = new Date();
    }

    continue() {
        if (!this.isPaused) {
            return;
        }
        console.log("Timer.continue()");
        var now = new Date();
        this.isPaused = false;
        this.startTime = now;
        if ((now - this.lastPauseTime) >= this.tolerableIdleInterval) {
            this.additionalTime -= this.idleWait;
        } else {
            this.additionalTime += (now - this.lastPauseTime);
        }
    }

    getTime() {
        console.log("Timer.getTime()");
        var duration = 0;
        for (var idx in this.sequences) {
            var sequenceElement = this.sequences[idx];
            duration += (sequenceElement.endTime - sequenceElement.startTime);
        }
        return duration + this.additionalTime;
    }
}

class RequirementPairPresenter {

    constructor(projectKey, currentUserID) {
        this.projectKey = projectKey;
        this.currentUserID = currentUserID;
        this.alreadyDefinedPotentialDependencies = {};
        this.unsurePairs = {};
        this.recommendedRequirementPairs = [];
        this.currentIndex = -1;
        this.showOnlyUnevaluatedPairs = false;
        this.timer = new Timer();
    }

    showNavigationList() {
        $("#or-requirement-pair-list").children().remove();
        for (var i in this.recommendedRequirementPairs) {
            var requirementPair = this.recommendedRequirementPairs[i];
            var sourceRequirementID = requirementPair.sourceRequirementID;
            var targetRequirementID = requirementPair.targetRequirementID;

            var listItemDiv = $("<div></div>")
                .attr("id", "or-requirement-pair-list-item-" + i)
                .addClass("or-requirement-pair-list-item")
                .append("Requirement Pair #" + (parseInt(i) + 1))
                .attr("data-requirement-pair-index", i);
            var uniqueKey = sourceRequirementID + "-" + targetRequirementID + "-" + this.currentUserID;
            if (uniqueKey in this.alreadyDefinedPotentialDependencies) {
                listItemDiv
                    .addClass("or-requirement-pair-evaluated or-requirement-pair-" + uniqueKey)
                    .append($("<span></span>").addClass("or-evaluated-icon").append("<i class=\"material-icons or-icon-ok\">check</i>"));
            } else {
                listItemDiv.addClass("or-requirement-pair-unevaluated or-requirement-pair-" + uniqueKey);
                if (uniqueKey in this.unsurePairs) {
                    listItemDiv.append($("<span></span>").addClass("or-evaluated-icon").append("<i class=\"material-icons or-icon-skipped\">?</i>"));
                } else {
                    listItemDiv.append($("<span></span>").addClass("or-evaluated-icon").append("<i class=\"material-icons or-icon-missing\">error_outline</i>"));
                }
            }
            $("#or-requirement-pair-list").append(listItemDiv);
        }
        $("#or-requirement-pair-list").show();
        bindRegisteredUIEvents(this);
    }

    showSpecificRequirementPair(event, thisObj) {
        var requirementPairIndex = parseInt($(thisObj).attr("data-requirement-pair-index"));
        this.currentIndex = requirementPairIndex;
        this.showCurrentRequirementPair();
        this.updateVisibilityOfViews(false);
    }

    showCurrentRequirementPair() {
        this.timer.start();
        var requirementPair = this.recommendedRequirementPairs[this.currentIndex];
        var sourceRequirementDescription = requirementPair.sourceRequirementDescription;
        var targetRequirementDescription = requirementPair.targetRequirementDescription;
        $("#or-source-dependency-description").text(sourceRequirementDescription);
        $("#or-target-dependency-description").text(targetRequirementDescription);
        $(".or-requirement-pair-list-item").removeClass("or-list-item-highlight");
        $("#or-requirement-pair-list-item-" + this.currentIndex).addClass("or-list-item-highlight");
    }

    showPreviousRequirementPair(event, thisObj) {
        if (this.currentIndex <= 0) {
            return;
        }
        --this.currentIndex;
        this.showCurrentRequirementPair();
        this.updateVisibilityOfViews(true);
    }

    showNextRequirementPair(event, thisObj) {
        var numberOfRequirementPairs = this.recommendedRequirementPairs.length;
        if (this.currentIndex >= (numberOfRequirementPairs - 1)) {
            return;
        }
        ++this.currentIndex;
        this.showCurrentRequirementPair();
        this.updateVisibilityOfViews(true);
    }

    updateVisibilityOfViews(autoScroll) {
        var numberOfRequirementPairs = this.recommendedRequirementPairs.length;
        var percentageInt = Math.round((this.currentIndex * 100.0) / (numberOfRequirementPairs - 1));
        $(".progress-text").text((this.currentIndex + 1) + "/" + numberOfRequirementPairs);
        $(".progress-bar").attr("aria-valuenow", percentageInt).css("width", percentageInt + "%");
        $(".sr-only").text(percentageInt + "% complete");

        $("#or-show-previous-requirement-pair").removeClass("disabled");
        $("#or-show-next-requirement-pair").removeClass("disabled");

        if (this.currentIndex <= 0) {
            $("#or-show-previous-requirement-pair").addClass("disabled");
        }

        if (this.currentIndex >= (numberOfRequirementPairs - 1)) {
            $("#or-show-next-requirement-pair").addClass("disabled");
        }

        if (this.showOnlyUnevaluatedPairs) {
            $(".or-requirement-pair-evaluated").hide();
        } else {
            $(".or-requirement-pair-evaluated").show();
        }

        if (autoScroll) {
            this.scrollToCurrentRequirementPair();
        }

        var requirementPair = this.recommendedRequirementPairs[this.currentIndex];
        var sourceRequirementID = requirementPair.sourceRequirementID;
        var targetRequirementID = requirementPair.targetRequirementID;
        var uniqueKey = sourceRequirementID + "-" + targetRequirementID + "-" + this.currentUserID;

        $(".or-dependency-option").prop("checked", false).removeAttr("checked");
        $("#or-dependency-type").hide();
        $("#or-dependency-direction").hide();
        var availableTypes = ["REQUIRES", "EXCLUDES", "SIMILAR", "PART_OF"];
        if (uniqueKey in this.alreadyDefinedPotentialDependencies) {
            var potentialDependencyData = this.alreadyDefinedPotentialDependencies[uniqueKey];
            if (potentialDependencyData.type.toLowerCase() == "none") {
                $("#or-dependency-no").prop("checked", true);
                $("#or-dependency-no").attr("checked", "checked");
            } else {
                $("#or-dependency-yes").prop("checked", true);
                $("#or-dependency-yes").attr("checked", "checked");
                $("#or-dependency-type").show();
                var typeIndex = availableTypes.indexOf(potentialDependencyData.type);
                $("#or-dependency-type-chooser").val(typeIndex);
                if ((potentialDependencyData.type.toLowerCase() == "requires") || (potentialDependencyData.type.toLowerCase() == "part_of")) {
                    if (potentialDependencyData.type.toLowerCase() == "requires") {
                        $(".or-dependency-direction-opt").each(function () {
                            $(this).text($(this).text().replace("IS PART OF", "REQUIRES"));
                        });
                    } else if (potentialDependencyData.type.toLowerCase() == "part_of") {
                        $(".or-dependency-direction-opt").each(function () {
                            $(this).text($(this).text().replace("REQUIRES", "IS PART OF"));
                        });
                    }
                    $("#or-dependency-direction").show();
                    $("#or-dependency-direction-chooser").val(potentialDependencyData.isReverseDirection ? "1" : "0");
                }
            }
        } else if (uniqueKey in this.unsurePairs) {
            $("#or-dependency-unsure").prop("checked", true);
            $("#or-dependency-unsure").attr("checked", "checked");
        }
        bindRegisteredUIEvents(this);
    }

    scrollToCurrentRequirementPair() {
        var divContainer = $("#or-requirement-pair-list");
        var scrollToClass = $(".or-list-item-highlight");
        divContainer.animate({
            scrollTop: scrollToClass.offset().top - divContainer.offset().top + divContainer.scrollTop() - 8,
            scrollLeft: 0
        }, 100);
    }

    selectMenuItem(event, thisObj) {
        var checkBox = $(thisObj).children("span").children(".or-checkbox");
        if (checkBox.is(':checked')) {
            checkBox.prop("checked", false);
            checkBox.removeAttr("checked");
            this.showOnlyUnevaluatedPairs = false;
        } else {
            checkBox.prop("checked", true);
            checkBox.attr("checked", "checked");
            this.showOnlyUnevaluatedPairs = true;
        }

        var selector = null;
        if (this.showOnlyUnevaluatedPairs) {
            var currentRequirementPair = $(".or-list-item-highlight");
            if (currentRequirementPair.hasClass("or-requirement-pair-evaluated")) {
                selector = $(".or-requirement-pair-unevaluated");
                if (selector.length == 0) {
                    selector = $(".or-requirement-pair-evaluated");
                }

                var newCurrentIndex = parseInt(selector.first().attr("data-requirement-pair-index"));
                this.currentIndex = newCurrentIndex;
                this.showCurrentRequirementPair();
            }
            $(".or-requirement-pair-evaluated").hide();
        } else {
            selector = $(".or-requirement-pair-evaluated");
            if (selector.length == 0) {
                selector = $(".or-requirement-pair-unevaluated");
            }
            var newCurrentIndex = parseInt(selector.first().attr("data-requirement-pair-index"));
            this.currentIndex = newCurrentIndex;
            this.showCurrentRequirementPair();
            $(".or-requirement-pair-evaluated").show();
        }

        this.updateVisibilityOfViews(false);
        this.scrollToCurrentRequirementPair();
        return false;
    }

    selectDependencyExists(event, thisObj) {
        event.stopPropagation();
        event.preventDefault();
        $(".or-dependency-option").prop("checked", false).removeAttr("checked");
        $("#or-dependency-yes").prop("checked", true);
        $("#or-dependency-yes").attr("checked", "checked");
        $("#or-dependency-direction").hide();
        $("#or-dependency-type").show();
        $("#or-dependency-type-chooser").val("");
        $("#or-dependency-direction-chooser").val("");
        bindRegisteredUIEvents(this);
        return false;
    }

    selectDependencyType(event, thisObj) {
        var availableTypes = ["REQUIRES", "EXCLUDES", "SIMILAR", "PART_OF"];
        var type = event.target.value;
        var typeStr = availableTypes[type];
        var isDirectionDependentDependency = (typeStr == "REQUIRES") || (typeStr == "PART_OF");
        $("#or-dependency-type").attr("data-selected-type", typeStr);

        if (isDirectionDependentDependency) {
            $("#or-dependency-direction").show();
            $("#or-dependency-direction-chooser").val("");
            if (typeStr == "REQUIRES") {
                $(".or-dependency-direction-opt").each(function () {
                    $(this).text($(this).text().replace("IS PART OF", "REQUIRES"));
                });
            } else if (typeStr == "PART_OF") {
                $(".or-dependency-direction-opt").each(function () {
                    $(this).text($(this).text().replace("REQUIRES", "IS PART OF"));
                });
            }
            bindRegisteredUIEvents(this);
            return false;
        }

        $("#or-dependency-direction").hide();
        this.saveClassifiedDependency(event, thisObj, typeStr, false);
        return false;
    }

    selectDependencyDirection(event, thisObj) {
        var isReverseDirection = (event.target.value == 1);
        var selectedTypeStr = $("#or-dependency-type").attr("data-selected-type");
        this.saveClassifiedDependency(event, thisObj, selectedTypeStr, isReverseDirection);
        return false;
    }

    saveClassifiedDependency(event, thisObj, typeStr, isReverseDirection) {
        var requirementPair = this.recommendedRequirementPairs[this.currentIndex];
        var sourceRequirementID = requirementPair.sourceRequirementID;
        var targetRequirementID = requirementPair.targetRequirementID;
        var uniqueKey = sourceRequirementID + "-" + targetRequirementID + "-" + this.currentUserID;

        if (typeStr === undefined) {
            if (!(uniqueKey in this.alreadyDefinedPotentialDependencies)) {
                return false;
            }
            var potentialDependencyData = this.alreadyDefinedPotentialDependencies[uniqueKey];
            typeStr = potentialDependencyData.type;
        }

        var isDirectionDependentDependency = (typeStr == "REQUIRES") || (typeStr == "PART_OF");

        if (uniqueKey in this.alreadyDefinedPotentialDependencies) {
            var potentialDependencyData = this.alreadyDefinedPotentialDependencies[uniqueKey];
            if (potentialDependencyData.type.toLowerCase() == typeStr.toLowerCase()) {
                if (!isDirectionDependentDependency || potentialDependencyData.isReverseDirection == isReverseDirection) {
                    // avoid sending redundant requests
                    console.log("Avoid sending redundant request!");
                    return false;
                }
            }
        }

        this.timer.pause();
        var duration = this.timer.getTime();
        var classifiedRequirementPairDto = {
            sourceRequirementID: requirementPair.sourceRequirementID,
            targetRequirementID: requirementPair.targetRequirementID,
            dependencyType: typeStr,
            dependencyExists: true,
            reverseDirection: isReverseDirection,
            duration: duration
        };

        var presenter = this;

        $.ajax("/project/" + this.projectKey + "/requirement/pair/classify.json", {
            'data': JSON.stringify(classifiedRequirementPairDto),
            'type': 'POST',
            'contentType': 'application/json',
            'processData': false,
            'success': function (result) {
                presenter.timer.continue();
                if (result.error == true) {
                    swal("Error", result.errorMessage, "error");
                    return false;
                }

                var definedPotentialDependency = result.definedPotentialDependency;
                var uniqueKey = definedPotentialDependency.uniqueKey;
                presenter.alreadyDefinedPotentialDependencies[uniqueKey] = definedPotentialDependency;
                if (uniqueKey in presenter.unsurePairs) {
                    delete presenter.unsurePairs[uniqueKey];
                }
                $(".or-requirement-pair-" + uniqueKey).addClass("or-requirement-pair-evaluated");
                $(".or-requirement-pair-" + uniqueKey).removeClass("or-requirement-pair-unevaluated");
                var evaluatedIcon = $(".or-requirement-pair-" + uniqueKey + " > .or-evaluated-icon");
                evaluatedIcon.children().remove();
                evaluatedIcon.append("<i class=\"material-icons or-icon-ok\">check</i>");
                showSavedToast();
            }
        });
    }

    selectDependencyNotExists(event, thisObj) {
        event.stopPropagation();
        event.preventDefault();
        $(".or-dependency-option").prop("checked", false).removeAttr("checked");
        $("#or-dependency-no").prop("checked", true);
        $("#or-dependency-no").attr("checked", "checked");
        $("#or-dependency-type").hide();
        $("#or-dependency-direction").hide();

        var requirementPair = this.recommendedRequirementPairs[this.currentIndex];
        var sourceRequirementID = requirementPair.sourceRequirementID;
        var targetRequirementID = requirementPair.targetRequirementID;
        var uniqueKey = sourceRequirementID + "-" + targetRequirementID + "-" + this.currentUserID;

        if (uniqueKey in this.alreadyDefinedPotentialDependencies) {
            var potentialDependencyData = this.alreadyDefinedPotentialDependencies[uniqueKey];
            if (potentialDependencyData.type.toLowerCase() == "none") {
                // avoid sending redundant requests
                return false;
            }
        }

        this.timer.pause();
        var duration = this.timer.getTime();
        var classifiedRequirementPairDto = {
            sourceRequirementID: requirementPair.sourceRequirementID,
            targetRequirementID: requirementPair.targetRequirementID,
            dependencyExists: false,
            reverseDirection: false,
            duration: duration
        };

        var presenter = this;

        $.ajax("/project/" + this.projectKey + "/requirement/pair/classify.json", {
            'data': JSON.stringify(classifiedRequirementPairDto),
            'type': 'POST',
            'contentType': 'application/json',
            'processData': false,
            'success': function (result) {
                presenter.timer.continue();
                if (result.error == true) {
                    swal("Error", result.errorMessage, "error");
                    return false;
                }

                var definedPotentialDependency = result.definedPotentialDependency;
                var uniqueKey = definedPotentialDependency.uniqueKey;
                presenter.alreadyDefinedPotentialDependencies[uniqueKey] = definedPotentialDependency;
                if (uniqueKey in presenter.unsurePairs) {
                    delete presenter.unsurePairs[uniqueKey];
                }
                $(".or-requirement-pair-" + uniqueKey).addClass("or-requirement-pair-evaluated");
                $(".or-requirement-pair-" + uniqueKey).removeClass("or-requirement-pair-unevaluated");
                var evaluatedIcon = $(".or-requirement-pair-" + uniqueKey + " > .or-evaluated-icon");
                evaluatedIcon.children().remove();
                evaluatedIcon.append("<i class=\"material-icons or-icon-ok\">check</i>");
                showSavedToast();
            }
        });
        return false;
    }

    selectRemoveDependency(event, thisObj) {
        event.stopPropagation();
        event.preventDefault();
        $(".or-dependency-option").prop("checked", false).removeAttr("checked");
        $("#or-dependency-unsure").prop("checked", true);
        $("#or-dependency-unsure").attr("checked", "checked");
        $("#or-dependency-type").hide();
        $("#or-dependency-direction").hide();

        var requirementPair = this.recommendedRequirementPairs[this.currentIndex];
        var sourceRequirementID = requirementPair.sourceRequirementID;
        var targetRequirementID = requirementPair.targetRequirementID;
        var uniqueKey = sourceRequirementID + "-" + targetRequirementID + "-" + this.currentUserID;

        if (uniqueKey in this.unsurePairs) {
            return false;
        }

        this.timer.pause();
        var classifiedRequirementPairDto = {
            sourceRequirementID: requirementPair.sourceRequirementID,
            targetRequirementID: requirementPair.targetRequirementID,
            dependencyExists: false,
            reverseDirection: false
        };

        var presenter = this;

        $.ajax("/project/" + this.projectKey + "/requirement/pair/unclassify.json", {
            'data': JSON.stringify(classifiedRequirementPairDto),
            'type': 'POST',
            'contentType': 'application/json',
            'processData': false,
            'success': function (result) {
                presenter.timer.continue();
                if (result.error == true) {
                    swal("Error", result.errorMessage, "error");
                    return false;
                }

                var uniqueKey = result.uniqueKey;
                delete presenter.alreadyDefinedPotentialDependencies[uniqueKey];
                presenter.unsurePairs[uniqueKey] = true;
                $(".or-requirement-pair-" + uniqueKey).addClass("or-requirement-pair-unevaluated");
                $(".or-requirement-pair-" + uniqueKey).removeClass("or-requirement-pair-evaluated");
                var evaluatedIcon = $(".or-requirement-pair-" + uniqueKey + " > .or-evaluated-icon");
                evaluatedIcon.children().remove();
                evaluatedIcon.append("<i class=\"material-icons or-icon-skipped\">?</i>");
                showSavedToast();
            }
        });
        return false;
    }

}
