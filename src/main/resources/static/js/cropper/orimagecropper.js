
class ImageCropper {

    constructor(imageLayer, uploadURL, minCropBoxWidth) {
        this.imageLayer = imageLayer;
        this.uploadURL = uploadURL;
        this.minCropBoxWidth = minCropBoxWidth;
    }

    run() {
        $(".or-image-upload-loader").hide();
        this.imageLayer.cropper({
            aspectRatio: 1,
            crop: function(event) {
                /*
                console.log(event.detail.x);
                console.log(event.detail.y);
                console.log(event.detail.width);
                console.log(event.detail.height);
                console.log(event.detail.rotate);
                console.log(event.detail.scaleX);
                console.log(event.detail.scaleY);
                */
            },
            background: true,
            autoCrop: true,
            minCropBoxWidth: this.minCropBoxWidth,
            autoCropArea: 1.0,
            viewMode: 1,
            ready: function (event) {}
        });
        this.registerEvents();
    }

    registerEvents() {
        var $image = this.imageLayer;
        $(".or-imagecropper-zoom-in").click(function () {
            $image.cropper("zoom", 0.1);
            return false;
        });

        $(".or-imagecropper-zoom-out").click(function () {
            $image.cropper("zoom", -0.1);
            return false;
        });

        $(".or-imagecropper-rotate-left").click(function () {
            $image.cropper("rotate", -45);
            return false;
        });

        $(".or-imagecropper-rotate-right").click(function () {
            $image.cropper("rotate", 45);
            return false;
        });

        $(".or-imagecropper-flip-horizontal").click(function () {
            var data = $image.data('cropper');
            $image.cropper("scaleX", (data.imageData.scaleX == 1) ? -1 : 1);
            return false;
        });

        $(".or-imagecropper-flip-vertical").click(function () {
            var data = $image.data('cropper');
            $image.cropper("scaleY", (data.imageData.scaleY == 1) ? -1 : 1);
            return false;
        });

        $(".or-imagecropper-crop").click(function () {
            $image.cropper("crop");
            return false;
        });

        $(".or-imagecropper-reset").click(function () {
            $image.cropper("reset");
            return false;
        });

        var uploadURL = this.uploadURL;
        $(".or-imagecropper-upload").click(function () {
            var width = $(".or-image-upload-container").width();
            var height = $(".or-image-upload-container").height();
            $(".or-image-upload-loader").show()
                .attr("style", "position:absolute;width:" + width +"px;height:" + height + "px;background:black;z-index:999");
            var croppedCanvas = $image.cropper("getCroppedCanvas", { maxWidth: 4096, maxHeight: 4096 });

            croppedCanvas.toBlob(function (blob) {
                var formData = new FormData();
                formData.append('croppedImage', blob);

                $.ajax(uploadURL, {
                    method: "POST",
                    data: formData,
                    processData: false,
                    contentType: false,
                    enctype: 'multipart/form-data',
                    cache:false,
                    beforeSend: function() {
                        var percentVal = '0%';
                        console.log(percentVal);
                        $(".cropper-container");
                    },
                    xhr: function() {
                        var myXhr = $.ajaxSettings.xhr();
                        if(myXhr.upload){
                            myXhr.upload.addEventListener('progress',progress, false);
                        }
                        return myXhr;
                    },
                    success() {
                        console.log('Upload success');
                        $(".or-image-upload-loader").hide();
                    }, error() {
                        console.log('Upload error');
                        $(".or-image-upload-loader").hide();
                    }
                });
            }, 'image/jpeg');
            return false;
        });
    }

}

function progress(e){

    if(e.lengthComputable){
        var max = e.total;
        var current = e.loaded;

        var percentage = (current * 100)/max;
        console.log(percentage);

        if (percentage >= 100) {
            // process completed
        }
    }
}
