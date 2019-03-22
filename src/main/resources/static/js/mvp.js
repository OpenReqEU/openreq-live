var TOP_LEVEL_DOMAINS = [
	'ac', 'ad', 'ae', 'aero', 'af', 'ag', 'ai', 'al', 'am', 'an', 'ao',
	'aq', 'ar', 'arpa', 'as', 'asia', 'at', 'au', 'aw', 'ax', 'az', 'ba',
	'bb', 'bd', 'be', 'bf', 'bg', 'bh', 'bi', 'biz', 'bj', 'bl', 'bm',
	'bn', 'bo', 'bq', 'br', 'bs', 'bt', 'bv', 'bw', 'by', 'bz', 'ca',
	'cat', 'cc', 'cd', 'cf', 'cg', 'ch', 'ci', 'ck', 'cl', 'cm', 'cn',
	'co', 'com', 'coop', 'cr', 'cs', 'cu', 'cv', 'cw', 'cx', 'cy', 'cz',
	'dd', 'de', 'dj', 'dk', 'dm', 'do', 'dz', 'ec', 'edu', 'ee', 'eg',
	'eh', 'er', 'es', 'et', 'eu', 'fi', 'fj', 'fk', 'fm', 'fo', 'fr',
	'ga', 'gb', 'gd', 'ge', 'gf', 'gg', 'gh', 'gi', 'gl', 'gm', 'gn',
	'gov', 'gp', 'gq', 'gr', 'gs', 'gt', 'gu', 'gw', 'gy', 'hk', 'hm',
	'hn', 'hr', 'ht', 'hu', 'id', 'ie', 'il', 'im', 'in', 'info', 'int',
	'io', 'iq', 'ir', 'is', 'it', 'je', 'jm', 'jo', 'jobs', 'jp', 'ke',
	'kg', 'kh', 'ki', 'km', 'kn', 'kp', 'kr', 'kw', 'ky', 'kz', 'la',
	'lb', 'lc', 'li', 'lk', 'local', 'lr', 'ls', 'lt', 'lu', 'lv', 'ly',
	'ma', 'mc', 'md', 'me', 'mf', 'mg', 'mh', 'mil', 'mk', 'ml', 'mm',
	'mn', 'mo', 'mobi', 'mp', 'mq', 'mr', 'ms', 'mt', 'mu', 'museum',
	'mv', 'mw', 'mx', 'my', 'mz', 'na', 'name', 'nato', 'nc', 'ne',
	'net', 'nf', 'ng', 'ni', 'nl', 'no', 'np', 'nr', 'nu', 'nz', 'om',
	'onion', 'org', 'pa', 'pe', 'pf', 'pg', 'ph', 'pk', 'pl', 'pm', 'pn',
	'pr', 'pro', 'ps', 'pt', 'pw', 'py', 'qa', 're', 'ro', 'rs', 'ru',
	'rw', 'sa', 'sb', 'sc', 'sd', 'se', 'sg', 'sh', 'si', 'sj', 'sk',
	'sl', 'sm', 'sn', 'so', 'sr', 'ss', 'st', 'su', 'sv', 'sx', 'sy',
	'sz', 'tc', 'td', 'tel', 'tf', 'tg', 'th', 'tj', 'tk', 'tl', 'tm',
	'tn', 'to', 'tp', 'tr', 'travel', 'tt', 'tv', 'tw', 'tz', 'ua', 'ug',
	'uk', 'um', 'us', 'uy', 'uz', 'va', 'vc', 've', 'vg', 'vi', 'vn',
	'vu', 'wf', 'ws', 'xxx', 'ye', 'yt', 'yu', 'za', 'zm', 'zr', 'zw'
];

var ICON_NAMES = [
    'build', 'location_city', 'format_color_reset', 'donut_small', 'surround_sound',
    'time_to_leave', 'flash_on', 'phone_forwarded', 'closed_caption', 'notifications_paused',
    'video_library', 'keyboard_voice', 'thumbs_up_down', 'local_printshop', 'library_add',
    'exposure_zero', 'my_location', 'cloud', 'notifications_none', 'featured_play_list',
    'desktop_windows', 'chevron_left', 'local_hotel', 'remove_circle', 'domain',
    'airline_seat_legroom_reduced', 'offline_pin', 'delete_forever', 'call', 'local_laundry_service',
    'linked_camera', 'view_module', 'mail', 'cancel', 'cloud_off', 'panorama_vertical',
    'settings_input_component', 'access_time', 'widgets', 'call_merge', 'group', 'blur_linear',
    'remove_circle_outline', 'aspect_ratio', 'av_timer', 'clear', 'keyboard_arrow_left',
    'open_in_new', 'near_me', 'restaurant_menu', 'short_text', 'show_chart', 'drag_handle',
    'picture_in_picture_alt', 'find_in_page', 'lock_outline', 'play_arrow', 'folder',
    'merge_type', 'stay_current_portrait', 'arrow_back', 'party_mode', 'format_indent_decrease',
    'network_check', 'assignment', 'gps_off', 'toys', 'library_music', 'wb_auto', 'view_carousel',
    'format_align_left', 'theaters', 'alarm_add', 'brightness_auto', 'voicemail', 'rotate_90_degrees_ccw',
    'leak_remove', 'playlist_add_check', 'rounded_corner', 'zoom_out', 'undo', 'assignment_turned_in',
    'screen_rotation', 'format_color_fill', 'grid_off', 'filter_1', 'pause_circle_filled', 'local_pharmacy', 'pin_drop',
    'chat_bubble_outline', 'settings_system_daydream', 'perm_media', 'eject', 'headset_mic', 'child_care', 'list',
    'swap_vertical_circle', 'camera_roll', 'directions', 'autorenew', 'local_parking', 'pages', 'plus_one',
    'accessibility', 'phone_in_talk', 'gradient', 'nfc', 'screen_lock_portrait', 'skip_previous', 'trending_down',
    'laptop_windows', 'gamepad', 'all_inclusive', 'filter_vintage', 'vignette', 'schedule', 'wifi_lock',
    'photo_size_select_large', 'fiber_dvr', 'notifications_off', 'flash_auto', 'phone_missed', 'texture', 'tune',
    'perm_identity', 'payment', 'queue', 'label_outline', 'crop_original', 'smoke_free', 'view_headline', 'shuffle',
    'dns', 'lock', 'looks_one', 'more_horiz', 'border_color', 'code', 'place', 'crop_5_4', 'location_searching',
    'phone_bluetooth_speaker', 'settings_cell', 'format_textdirection_r_to_l', 'invert_colors_off', 'favorite_border',
    'brightness_high', 'report_problem', 'collections', 'hot_tub', 'fiber_new', 'access_alarm', 'bluetooth', 'edit',
    'landscape', 'fiber_pin', 'tag_faces', 'traffic', 'system_update', 'call_received', 'low_priority',
    'supervisor_account', 'zoom_in', 'subject', 'battery_alert', 'settings_input_hdmi', 'visibility_off',
    'notifications', 'laptop', 'check_box_outline_blank', 'cake', 'reply', 'healing', 'first_page',
    'signal_cellular_4_bar', 'help_outline', 'replay', 'hearing', 'error', 'local_shipping', 'photo_size_select_actual',
    'battery_full', 'portable_wifi_off', 'print', 'directions_railway', 'done', 'control_point_duplicate',
    'filter_b_and_w', 'gif', 'gesture', 'content_cut', 'pie_chart', 'store', 'data_usage', 'equalizer', 'compare',
    'layers_clear', 'sim_card', 'navigation', 'record_voice_over', 'move_to_inbox', 'burst_mode', 'mouse',
    'create_new_folder', 'announcement', 'forward_5', 'child_friendly', 'speaker', 'get_app', 'arrow_drop_down',
    'trending_flat', 'fast_forward', 'format_underlined', 'watch', 'snooze', 'import_export', 'import_contacts',
    'folder_open', 'assistant_photo', 'battery_unknown', 'error_outline', 'gps_not_fixed', 'call_split', 'iso',
    'loyalty', 'network_locked', 'picture_in_picture', 'satellite', 'sim_card_alert', 'assignment_late', 'ring_volume',
    'directions_car', 'filter_3', 'system_update_alt', 'border_top', 'lens', 'settings_overscan', 'panorama_horizontal',
    'clear_all', 'local_phone', 'class', 'library_books', 'looks', 'audiotrack', 'sentiment_very_dissatisfied',
    'not_interested', 'bubble_chart', 'blur_off', 'crop_7_5', 'fiber_manual_record', 'delete', 'notifications_active',
    'text_fields', 'font_download', 'filter_9', 'airplay', 'accessible', 'phone_locked', 'swap_horiz', 'contact_mail',
    'attach_money', 'layers', 'blur_circular', 'live_help', 'directions_subway', 'receipt', 'timelapse',
    'event_available', 'hotel', 'live_tv', 'phonelink', 'screen_lock_landscape', 'forward', 'strikethrough_s',
    'sentiment_dissatisfied', 'settings_input_svideo', 'text_format', 'sms_failed', 'train', 'check', 'dock', 'dialpad',
    'person_add', 'backspace', 'attach_file', 'smoking_rooms', 'airline_seat_recline_normal', 'info',
    'photo_size_select_small', 'security', 'exposure', 'chrome_reader_mode', 'usb', 'apps', 'business_center',
    'assignment_return', 'airplanemode_inactive', 'favorite', 'stay_primary_landscape', 'pool', 'extension',
    'airport_shuttle', 'local_pizza', 'switch_camera', 'movie', 'public', 'art_track', 'tablet_mac',
    'play_circle_outline', 'photo_camera', 'directions_walk', 'leak_add', 'sort_by_alpha', 'settings', 'videocam',
    'account_circle', 'bluetooth_searching', 'select_all', 'today', 'wb_iridescent', 'visibility', 'add_shopping_cart',
    'ev_station', 'airline_seat_flat', 'border_outer', 'movie_creation', 'thumb_up', 'add_location', 'contacts', 'mic',
    'location_off', 'local_play', 'flip', 'functions', 'settings_input_antenna', 'work', 'directions_boat',
    'add_circle_outline', 'blur_on', 'local_hospital', 'looks_4', 'assistant', 'smartphone', 'camera', 'photo_filter',
    'priority_high', 'format_align_right', 'format_paint', 'local_library', 'subscriptions', 'ac_unit',
    'developer_board', 'help', 'keyboard_arrow_down', 'local_florist', 'gavel', 'arrow_drop_down_circle',
    'arrow_drop_up', 'network_wifi', 'exposure_neg_2', 'do_not_disturb_off', 'ondemand_video', 'looks_3', 'opacity',
    'restore', 'filter_8', 'settings_power', 'flight_land', 'grid_on', 'call_to_action', 'wc', 'wb_incandescent',
    'add_a_photo', 'alarm_off', 'shopping_cart', 'image_aspect_ratio', 'signal_wifi_4_bar', 'view_week', 'drafts',
    'folder_shared', 'highlight_off', 'room', 'alarm', 'phone_android', 'touch_app', 'android',
    'settings_input_composite', 'fingerprint', 'brightness_medium', 'looks_6', 'golf_course', 'insert_chart',
    'keyboard_backspace', 'local_dining', 'navigate_next', 'local_convenience_store', 'wallpaper', 'kitchen',
    'account_box', 'format_color_text', 'battery_std', 'brightness_2', 'nature', 'photo_album', 'mood', 'play_for_work',
    'add_alert', 'filter_5', 'tonality', 'add_to_queue', 'unfold_less', 'power_settings_new', 'wifi_tethering',
    'note_add', 'indeterminate_check_box', 'format_shapes', 'markunread_mailbox', 'update', 'hourglass_empty',
    'cloud_queue', 'thumb_down', 'dehaze', 'tab', 'add_alarm', 'spa', 'signal_cellular_no_sim', 'home', 'access_alarms',
    'view_list', 'perm_camera_mic', 'volume_down', 'polymer', 'adb', 'keyboard', 'directions_run', 'hdr_weak',
    'photo_library', 'restore_page', 'portrait', 'flight', 'mail_outline', 'lock_open', 'call_missed_outgoing', 'games',
    'inbox', 'recent_actors', 'radio', 'filter', 'title', 'directions_transit', 'unarchive', 'nature_people',
    'local_airport', 'room_service', 'contact_phone', 'local_gas_station', 'tablet_android', 'pageview', 'g_translate',
    'http', 'speaker_phone', 'view_array', 'center_focus_weak', 'mode_comment', 'expand_more', 'picture_as_pdf',
    'vertical_align_center', 'format_italic', 'explore', 'file_download', 'insert_link', 'format_line_spacing',
    'cloud_circle', 'rate_review', 'format_align_justify', 'event_busy', 'chevron_right', 'local_post_office',
    'border_clear', 'border_vertical', 'perm_scan_wifi', 'pregnant_woman', 'brush', 'next_week', 'mic_off', 'slideshow',
    'trending_up', 'rotate_left', 'phonelink_setup', 'comment', 'exit_to_app', '3d_rotation', 'crop_din',
    'featured_video', 'border_right', 'star_border', 'cached', 'important_devices', 'format_indent_increase',
    'file_upload', 'filter_drama', 'arrow_upward', 'photo', 'poll', 'format_size', 'change_history', 'navigate_before',
    'call_made', 'linear_scale', 'grade', 'hdr_strong', 'settings_bluetooth', 'gps_fixed', 'stay_primary_portrait',
    'screen_share', 'verified_user', 'sync_problem', 'headset', 'local_see', 'line_style', 'perm_contact_calendar',
    'check_circle', 'pets', 'speaker_notes_off', 'call_end', 'assignment_returned', 'screen_lock_rotation',
    'sentiment_very_satisfied', 'location_disabled', 'replay_5', 'insert_drive_file', 'toll', 'explicit', 'replay_10',
    'feedback', 'language', 'filter_2', 'turned_in', 'credit_card', 'local_atm', 'view_stream', 'timer', 'video_call',
    'grain', 'bluetooth_audio', 'web', 'rowing', 'transfer_within_a_station', 'border_horizontal', 'donut_large',
    'phone_paused', 'image', 'swap_calls', 'local_grocery_store', 'tv', 'exposure_plus_1', 'settings_ethernet',
    'switch_video', 'style', 'do_not_disturb', 'play_circle_filled', 'timeline', 'local_activity', 'lightbulb_outline',
    'crop_3_2', 'computer', 'filter_list', 'colorize', 'local_offer', 'highlight', 'repeat', 'directions_bus',
    'format_list_bulleted', 'publish', 'message', 'location_on', 'border_all', 'videocam_off', 'refresh',
    'slow_motion_video', 'rss_feed', 'brightness_low', 'wb_cloudy', 'looks_two', 'store_mall_directory', 'view_comfy',
    'developer_mode', 'playlist_add', 'people', 'motorcycle', 'shop', 'transform', 'copyright', 'tab_unselected',
    'money_off', 'branding_watermark', 'local_movies', 'search', 'line_weight', 'settings_voice', 'beenhere', 'palette',
    'scanner', 'local_cafe', 'signal_cellular_null', 'local_mall', 'adjust', 'people_outline', 'phone',
    'remove_shopping_cart', 'local_taxi', 'add_circle', 'sd_card', 'looks_5', 'playlist_play', 'tram', 'translate',
    'star_half', 'mic_none', 'laptop_chromebook', 'create', 'multiline_chart', 'perm_phone_msg', 'all_out',
    'new_releases', 'warning', 'person_pin', 'camera_alt', 'card_giftcard', 'devices_other', 'arrow_downward', 'loupe',
    'keyboard_hide', 'brightness_6', 'more_vert', 'textsms', 'pie_chart_outlined', 'border_left',
    'perm_device_information', 'speaker_group', 'fast_rewind', 'airline_seat_flat_angled', 'sync', 'drive_eta',
    'vertical_align_bottom', 'check_box', 'wrap_text', 'delete_sweep', 'personal_video', 'zoom_out_map',
    'settings_remote', 'shop_two', 'hd', 'fitness_center', 'subdirectory_arrow_right', 'pan_tool',
    'signal_cellular_off', 'video_label', 'book', 'filter_4', 'replay_30', 'turned_in_not', 'movie_filter',
    'format_bold', 'no_sim', 'crop_portrait', 'phonelink_erase', 'folder_special', 'wb_sunny', 'format_align_center',
    'mood_bad', 'compare_arrows', 'confirmation_number', 'album', 'queue_music', 'keyboard_tab', 'sentiment_satisfied',
    'person_outline', 'filter_tilt_shift', 'web_asset', 'insert_invitation', 'subtitles', 'content_paste', 'straighten',
    'keyboard_return', 'chat', 'sentiment_neutral', 'block', 'space_bar', 'expand_less', 'bug_report', 'view_column',
    'invert_colors', 'mode_edit', 'timer_off', 'toc', 'insert_emoticon', 'exposure_neg_1', 'flare', 'keyboard_arrow_up',
    'remove_red_eye', 'email', 'phonelink_ring', 'music_video', 'no_encryption', 'crop_landscape', 'cast_connected',
    'date_range', 'add_to_photos', 'airplanemode_active', 'event', 'add_box', 'memory', 'dvr', 'map',
    'airline_seat_legroom_extra', 'details', 'forum', 'archive', 'euro_symbol', 'edit_location', 'history',
    'tap_and_play', 'event_note', 'bluetooth_disabled', 'keyboard_arrow_right', 'terrain', 'directions_bike',
    'card_travel', 'chat_bubble', 'share', 'stars', 'stay_current_landscape', 'filter_frames', 'view_compact',
    'description', 'volume_off', 'do_not_disturb_alt', 'alarm_on', 'filter_hdr', 'router', 'brightness_1', 'spellcheck',
    'school', 'subway', 'business', 'filter_6', 'flip_to_back', 'local_drink', 'power_input', 'local_bar', 'done_all',
    'format_clear', 'timer_10', 'arrow_forward', 'devices', 'more', 'phonelink_lock', 'streetview', 'bookmark', 'wifi',
    'label', 'input', 'local_car_wash', 'keyboard_capslock', 'monetization_on', 'content_copy', 'restaurant',
    'cloud_upload', 'report', 'insert_photo', 'repeat_one', 'dialer_sip', 'pause', 'info_outline', 'person', 'power',
    'forward_30', 'group_work', 'signal_cellular_connected_no_internet_4_bar', 'flight_takeoff', 'remove_from_queue',
    'https', 'brightness_7', 'menu', 'stop_screen_share', 'track_changes', 'view_quilt', 'sms', 'fiber_smart_record',
    'settings_applications', 'event_seat', 'panorama_fish_eye', 'skip_next', 'flash_off', 'crop_free', 'volume_mute',
    'last_page', 'link', 'attachment', 'person_pin_circle', 'assignment_ind', 'storage', 'crop_16_9', 'laptop_mac',
    'fullscreen_exit', 'whatshot', 'bluetooth_connected', 'border_inner', 'crop', 'device_hub', 'face', 'high_quality',
    'settings_phone', 'subdirectory_arrow_left', 'tablet', 'airline_seat_individual_suite', 'do_not_disturb_on',
    'airline_seat_recline_extra', 'cloud_download', 'reply_all', 'rotate_right', 'camera_front', 'color_lens',
    'panorama', 'beach_access', 'filter_center_focus', 'assessment', 'call_missed', 'swap_vert', 'format_list_numbered',
    'casino', 'find_replace', 'format_textdirection_l_to_r', 'monochrome_photos', 'insert_comment', 'forward_10',
    'vertical_align_top', 'desktop_mac', 'signal_wifi_off', 'speaker_notes', 'present_to_all', 'queue_play_next',
    'collections_bookmark', 'crop_rotate', 'add', 'control_point', 'brightness_5', 'vpn_key', 'flip_to_front',
    'network_cell', 'question_answer', 'shopping_basket', 'disc_full', 'sort', 'camera_enhance', 'center_focus_strong',
    'phone_iphone', 'format_quote', 'rv_hookup', 'videogame_asset', 'free_breakfast', 'filter_7', 'open_with',
    'airline_seat_legroom_normal', 'format_strikethrough', 'loop', 'sd_storage', 'vpn_lock', 'voice_chat', 'volume_up',
    'weekend', 'cast', 'signal_wifi_4_bar_lock', 'markunread', 'query_builder', 'mms', 'music_note',
    'radio_button_unchecked', 'camera_rear', 'remove', 'redeem', 'timer_3', 'filter_9_plus', 'vibration', 'unfold_more',
    'bookmark_border', 'brightness_4', 'save', 'close', 'send', 'exposure_plus_2', 'crop_square', 'launch',
    'account_balance', 'flag', 'hourglass_full', 'group_add', 'border_style', 'brightness_3', 'perm_data_setting',
    'youtube_searched_for', 'phonelink_off', 'battery_charging_full', 'broken_image', 'backup', 'view_agenda',
    'hdr_off', 'border_bottom', 'radio_button_checked', 'sync_disabled', 'cloud_done', 'redo', 'hdr_on',
    'settings_brightness', 'graphic_eq', 'pause_circle_outline', 'dashboard', 'settings_backup_restore',
    'account_balance_wallet', 'filter_none', 'fullscreen', 'watch_later', 'reorder', 'card_membership', 'view_day',
    'star', 'open_in_browser', 'stop', 'enhanced_encryption', 'note', 'panorama_wide_angle'
];

function bindUIEvent(toObject, methodName) {
    return function(event) { toObject[methodName](event, this); return false; }
}

function bindEach(toObject, methodName, param) {
	return function() { toObject[methodName](this, param); }
}

function bindSaveHandlerNextEvent(toObject, methodName) {
	return function() { toObject[methodName](); }
}

function bindAJAXSuccessHandler(toObject, methodName) {
	return function(result, status, xhr) { toObject[methodName](this, result, status, xhr); }
}

function bindAJAXExtendedSuccessHandler(toObject, methodName, callback) {
	return function(result, status, xhr) { toObject[methodName](this, result, status, xhr, callback); }
}

function bindAJAXErrorHandler(toObject, methodName) {
	return function(xhr, status, error) { toObject[methodName](this, xhr, status, error); }
}

function truncate(str, length, ending) {

    if (length === undefined) {
        length = 100;
    }

    if (ending === undefined) {
        ending = '...';
    }

    if (str.length > length) {
        return str.substring(0, length - ending.length) + ending;
    }

    return str;
}

function formatDateText(date) {
	var monthNames = [
		"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
		"Aug", "Sep", "Oct", "Nov", "Dec"
	];
	var day = date.getDate();
	var monthIndex = date.getMonth();
	var year = String(date.getFullYear()).slice(-2);
	return monthNames[monthIndex] + ' ' + day;
}

function formatDate(date) {
	var month = (date.getMonth() + 1);
	var day = date.getDate();
	return date.getFullYear() + "-" + ((month < 10) ? "0" : "") + month + "-" + ((day < 10) ? "0" : "") + day;
}

function formatDateTime(date) {
    var month = (date.getMonth() + 1);
    var day = date.getDate();
    return date.getFullYear() + "-" + ((month < 10) ? "0" : "") + month + "-" + ((day < 10) ? "0" : "") + day + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
}

function getCookie(name) {
	var value = "; " + document.cookie;
	var parts = value.split("; " + name + "=");
	if (parts.length == 2) return parts.pop().split(";").shift();
}

function isValidEmailAddress(emailAddress) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(emailAddress).toLowerCase());
}

function isArrowKey(key) {
	return ([37, 38, 39, 40].indexOf(key) != -1);
}

function isEnterKey(key) {
    return (key == 13);
}

// based on: https://stackoverflow.com/a/5200010
function bySortedValue(obj, callback, context) {
    var tuples = [];

    for (var key in obj) tuples.push([key, obj[key]]);

    tuples.sort(function(a, b) {
        return a[1] < b[1] ? 1 : a[1] > b[1] ? -1 : 0
    });

    var length = tuples.length;
    while (length--) callback.call(context, tuples[length][0], tuples[length][1]);
}

function computeUserLiquidDemocracyDelegationImportance(ratingBackwardDelegations, userID) {
    var userImportance = 1;
    if (userID in ratingBackwardDelegations) {
        for (var i = 0; i < ratingBackwardDelegations[userID].length; i++) {
            var userIDOfDelegatingUser = ratingBackwardDelegations[userID][i].id;
            userImportance += computeUserLiquidDemocracyDelegationImportance(ratingBackwardDelegations, userIDOfDelegatingUser);
        }
    }
    return userImportance;
}

function computeAverageRating(ratings) {
	var sum = 0.0;
	var count = 0;
	for (var userID in ratings) {
        if ("value" in ratings[userID]) {
            sum += ratings[userID].value;
            ++count;
        }
	}
	return (count > 0) ? (sum / count) : 0.0;
}

function computeMAUT(ratingAttributeData, ratings, ratingForwardDelegations, ratingBackwardDelegations) {
	var ratingAttributeIDMap = {};
	for (var i in ratingAttributeData) {
		var ratingAttributeInfo = ratingAttributeData[i];
		ratingAttributeIDMap[ratingAttributeInfo.id] = ratingAttributeInfo;
	}

	var unweightedSumMap = {};
	var unweightedCountMap = {};
	for (var userID in ratings) {
        var userWeight = computeUserLiquidDemocracyDelegationImportance(ratingBackwardDelegations, userID);
	    var allUserRatingsData = ratings[userID];
	    for (var i in allUserRatingsData) {
	    	var ratingData = allUserRatingsData[i];
		    if (!(ratingData.attributeID in unweightedSumMap)) {
				unweightedSumMap[ratingData.attributeID] = 0.0;
				unweightedCountMap[ratingData.attributeID] = 0;
		    }
		    unweightedSumMap[ratingData.attributeID] += ratingData.value * userWeight;
			unweightedCountMap[ratingData.attributeID] += userWeight;
	    }
	}

	var sum = 0.0;
	var sumOfWeights = 0.0;
	for (var ratingAttributeID in unweightedSumMap) {
	    var ratingAttributeInfo = ratingAttributeIDMap[ratingAttributeID];
	    var factor = unweightedSumMap[ratingAttributeID] / unweightedCountMap[ratingAttributeID];
	    if (ratingAttributeInfo.isReverse) {
	    	factor = (ratingAttributeInfo.maxValue + ratingAttributeInfo.minValue) - factor;
	    }
	    sum += ratingAttributeInfo.weight * factor;
        sumOfWeights += ratingAttributeInfo.weight;
	}
	return (sumOfWeights > 0.0) ? (sum / sumOfWeights) : 0.0;
}

function convertMessagesToRatings(ratingAttributeData, requirementComments) {
    var ratingAttributeIDMap = {};
    for (var i in ratingAttributeData) {
        var ratingAttributeInfo = ratingAttributeData[i];
        ratingAttributeIDMap[ratingAttributeInfo.id] = ratingAttributeInfo;
    }

    var ratingValueMap = {
        "PRO": 5,
        "NEUTRAL": 3,
        "CON": 1
    };

    var ratings = {};
    for (var i in requirementComments) {
        var comment = requirementComments[i];
        var sentiment = comment.sentiment;
        var userID = comment.userData.userID;
        if (!(userID in ratings)) {
            ratings[userID] = [];
        }

        for (var j in comment.assignedDimensions) {
            var ratingAttributeID = comment.assignedDimensions[j];
            var ratingAttributeInfo = ratingAttributeIDMap[ratingAttributeID];
            var value = ratingValueMap[sentiment];
            if (ratingAttributeInfo.isReverse) {
                value = (ratingAttributeInfo.maxValue + ratingAttributeInfo.minValue) - value;
            }

            ratings[userID].push({
                attributeID: ratingAttributeID,
                userEmail: comment.userData.email,
                userFirstName: comment.userData.firstName,
                userLastName: comment.userData.lastName,
                value: value
            });
        }
    }
    return ratings;
}

function messagesWithConflicts(requirement) {
    var conflictingCommentIDs = {};
    for (var idx in requirement.advancedConflicts) {
        var conflict = requirement.advancedConflicts[idx];
        if (!(conflict.commentAID in conflictingCommentIDs)) {
            conflictingCommentIDs[conflict.commentAID] = [];
        }
        conflictingCommentIDs[conflict.commentAID].push(conflict.commentBID);
    }
    return conflictingCommentIDs;
}

function updateProConIndicator(proConIndicator, numberOfPros, numberOfNeus, numberOfCons) {
    var numberOfSentiments = parseFloat(numberOfPros + numberOfNeus + numberOfCons);
    var proPercentage = (numberOfSentiments > 0) ? Math.round(numberOfPros/numberOfSentiments * 1000)/10 : 0;
    var neuPercentage = (numberOfSentiments > 0) ? Math.round(numberOfNeus/numberOfSentiments * 1000)/10 : 0;
    var conPercentage = (numberOfSentiments > 0) ? Math.round(numberOfCons/numberOfSentiments * 1000)/10 : 0;
    var difference = (100.0 - (proPercentage + neuPercentage + conPercentage));

    proConIndicator.attr("data-pro-count", numberOfPros).attr("data-neu-count", numberOfNeus).attr("data-con-count", numberOfCons);
    proConIndicator.children().remove();
    proConIndicator.hide();

    if (numberOfPros > 0) {
        proPercentage += difference;
        difference = 0.0;
        proConIndicator.append($("<div></div>").addClass("or-pro-bar").css("width", proPercentage + "%").text(numberOfPros));
        proConIndicator.show();
    }

    if (numberOfNeus) {
        neuPercentage += difference;
        difference = 0.0;
        proConIndicator.append($("<div></div>").addClass("or-neu-bar").css("width", neuPercentage + "%").text(numberOfNeus));
        proConIndicator.show();
    }

    if (numberOfCons) {
        conPercentage += difference;
        proConIndicator.append($("<div></div>").addClass("or-con-bar").css("width", conPercentage + "%").text(numberOfCons));
        proConIndicator.show();
    }
}

function showBasicRatingConflict(inconsistencyLabel, basicRatings) {
	var averageCounts = 0.0;
	var ratingCounts = 0;
	var minRating = Number.POSITIVE_INFINITY;
	var maxRating = 0;
	for (var userID in basicRatings) {
		var userRating = basicRatings[userID];
		if (!("value" in userRating)) {
			continue;
		}

        averageCounts += userRating.value;
        ++ratingCounts;

        if (userRating.value < minRating) {
            minRating = userRating.value;
        }

        if (userRating.value > maxRating) {
            maxRating = userRating.value;
        }
	}

	var maximumAllowedDistance = 2.5; // TODO: outsource this threshold/constant
    var maxRatingDistance = maxRating - minRating;
    if (maxRatingDistance > maximumAllowedDistance) {
        var conflictMessage = "The ratings differ too much from each other. " +
            "Maximum distance is: " + maxRatingDistance.toFixed(2) + ". " +
            "However, the maximum allowed distance is " + maximumAllowedDistance + ".";
        var infoIconLink = $("<a></a>").addClass("or-rating-conflict-indicator")
            .attr("href", "javascript:void(0);")
            .attr("data-html", "true")
            .attr("data-toggle", "tooltip")
            .attr("data-placement", "bottom")
            .attr("title", conflictMessage);
        infoIconLink.append("<i class=\"material-icons\">flash_on</i>");
        inconsistencyLabel.html(infoIconLink);
        inconsistencyLabel.show();
    } else {
        inconsistencyLabel.hide();
    }

	$('[data-toggle="tooltip"]').tooltip();
}

function showRatingConflict(inconsistencyLabel, ratingAttributeData, ratings) {
	var averageCounts = {};
	var ratingCounts = {};
	var minRatings = {};
	var maxRatings = {};
	for (var userID in ratings) {
		var userRatings = ratings[userID];
		if (userRatings.length == 0) {
			continue;
		}

		for (var i in ratingAttributeData) {
			var ratingAttribute = ratingAttributeData[i];
			for (var j in userRatings) {
				var userRating = userRatings[j];
				if (ratingAttribute.id == userRating.attributeID) {
					if (!(ratingAttribute.id in averageCounts)) {
						averageCounts[ratingAttribute.id] = 0.0;
						ratingCounts[ratingAttribute.id] = 0;
						minRatings[ratingAttribute.id] = Number.POSITIVE_INFINITY;
						maxRatings[ratingAttribute.id] = 0;
					}
					averageCounts[ratingAttribute.id] += userRating.value;
					++ratingCounts[ratingAttribute.id];

					if (userRating.value < minRatings[ratingAttribute.id]) {
						minRatings[ratingAttribute.id] = userRating.value;
					}

					if (userRating.value > maxRatings[ratingAttribute.id]) {
						maxRatings[ratingAttribute.id] = userRating.value;
					}
					break;
				}
			}
		}
	}

	var conflictMessages = [];
	var distanceThreshold = 2.5;
	for (var ratingAttributeID in minRatings) {
		var maxRatingDistance = maxRatings[ratingAttributeID] - minRatings[ratingAttributeID];
		if (maxRatingDistance > distanceThreshold) {
			var conflictAttributeName = null;
			for (var i in ratingAttributeData) {
				var ratingAttribute = ratingAttributeData[i];
				if (ratingAttribute.id == ratingAttributeID) {
					conflictAttributeName = ratingAttribute.name;
					break;
				}
			}
			conflictMessages.push("The ratings of dimension '" + conflictAttributeName +
                "' differ too much from each other. The longest distance is " + maxRatingDistance.toFixed(2) +
                ". However, the maximum allowed distance is " + distanceThreshold + ".");
		}
	}

	if (conflictMessages.length > 0) {
		var infoIconLink = $("<a></a>").addClass("or-rating-conflict-indicator")
                                        .attr("href", "javascript:void(0);")
                                        .attr("data-html", "true")
                                        .attr("data-toggle", "tooltip")
                                        .attr("data-placement", "bottom")
                                        .attr("title", conflictMessages.join("\n\n"));
		infoIconLink.append("<i class=\"material-icons\">flash_on</i>");
		inconsistencyLabel.html(infoIconLink);
		inconsistencyLabel.show();
	} else {
		inconsistencyLabel.hide();
	}

	$('[data-toggle="tooltip"]').tooltip();
}

function showBasicScore(requirement, basicRatings, dataManager) {
    var averageResult = computeAverageRating(basicRatings);
    var ratingCount = 0;
    for (var userID in basicRatings) { if ("value" in basicRatings[userID]) { ++ratingCount; } }

    var field = $(".or-requirement-basic-evaluation-field-" + requirement.id);
    field.attr("data-current-rating", averageResult.toFixed(1));
    field.barrating("destroy").barrating("show", {
        theme: "fontawesome-stars-o",
        initialRating: averageResult.toFixed(1),
        allowEmpty: false,
        showSelectedRating: true,
        showValues: false,
        deselectable: false
    }).barrating("readonly", true);

    var evaluationRow = $("#or-requirement-" + requirement.id + " > .or-requirement-basic-evaluation");
    evaluationRow.children(".or-evaluation-result").text(averageResult.toFixed(2));
    $("#or-requirement-" + requirement.id + " > .or-requirement-basic-evaluation > .or-average-rating-user-count")
        .text(ratingCount + " user" + (ratingCount != 1 ? "s" : "") + " voted");

    var inconsistencyLabel = $("#or-requirement-" + requirement.id + " > .or-requirement-basic-evaluation > .or-average-rating-inconsistency");
    showBasicRatingConflict(inconsistencyLabel, basicRatings);
    dataManager.showReleasesRequirementsAndIssues();
}

function showMAUTScore(requirement, ratings, ratingAttributeData, dataManager) {
    var ratingCount = 0;
    for (var userID in ratings) {
        if (ratings[userID].length > 0) {
            ++ratingCount;
        }
    }

    var mautResult = computeMAUT(ratingAttributeData, ratings, requirement.ratingForwardDelegations, requirement.ratingBackwardDelegations);
    $("#or-requirement-" + requirement.id + " > .or-requirement-normal-evaluation > .center").text(mautResult.toFixed(2));
    $("#or-requirement-" + requirement.id + " > .or-requirement-normal-evaluation > .or-average-rating-user-count").text(ratingCount + " user" + (ratingCount != 1 ? "s" : "") + " voted");

    var inconsistencyLabel = $("#or-requirement-" + requirement.id + " > .or-requirement-normal-evaluation > .or-average-rating-inconsistency");
    showRatingConflict(inconsistencyLabel, ratingAttributeData, ratings);
    dataManager.showReleasesRequirementsAndIssues();
}

function showArgumentMAUTScore(inconsistencyLabel, requirement, requirementComments, ratingAttributeData, dataManager,
                               showConflictsOnly, refresh) {
    var requirementID = requirement.id;
    var ratingAttributeIDMap = {};
    for (var i in ratingAttributeData) {
        var ratingAttributeInfo = ratingAttributeData[i];
        ratingAttributeIDMap[ratingAttributeInfo.id] = ratingAttributeInfo;
    }
    var ratedUsers = {};
    for (var i in requirementComments) {
        ratedUsers[requirementComments[i].userData.userID] = true;
    }

    var ratingCount = Object.keys(ratedUsers).length;
    var ratings = convertMessagesToRatings(ratingAttributeData, requirementComments);
    var mautResult = computeMAUT(ratingAttributeData, ratings, {}, {});

    if (!showConflictsOnly) {
        $("#or-requirement-" + requirementID + " > .or-requirement-advanced-evaluation > .center")
            .text(mautResult.toFixed(2));
        $("#or-requirement-" + requirementID + " > .or-requirement-advanced-evaluation > .or-average-rating-user-count")
            .text(ratingCount + " user" + (ratingCount != 1 ? "s" : "") + " voted");
    }

    $(".argument-issues").children().remove();
    var conflicts = requirement.advancedConflicts;
    if (conflicts.length == 0) {
        inconsistencyLabel.hide();
    } else {
        var conflictMessages = [];
        var numOfConflicts = conflicts.length / 2;
        conflictMessages.push((numOfConflicts == 1)
            ? "There is 1 conflict:"
            : "There are " + numOfConflicts + " conflicts:");
        var observedConflicts = {};
        var attributeConflicts = {};

        for (var i in conflicts) {
            var conflictData = conflicts[i];
            var key = conflictData.userA.id + "-" + conflictData.userB.id + "-" + conflictData.ratingAttributeID;
            var reverseKey = conflictData.userB.id + "-" + conflictData.userA.id + "-" + conflictData.ratingAttributeID;
            if (key in observedConflicts || reverseKey in observedConflicts) {
                continue;
            }
            observedConflicts[key] = true;
            observedConflicts[reverseKey] = true;

            var attributeName = ratingAttributeIDMap[conflictData.ratingAttributeID].name;
            if (!(conflictData.ratingAttributeID in attributeConflicts)) {
                attributeConflicts[conflictData.ratingAttributeID] = [];
            }

            var attributeConflictMessages = attributeConflicts[conflictData.ratingAttributeID];
            var message = "";
            var specificMessage = "";
            if (conflictData.userA.id == conflictData.userB.id) {
                message = "You provided at least one PRO and one CON argument";
                specificMessage = message + " for the same dimension \"" + attributeName + "\".";
                message += ".";
            } else {
                message = "There exist PRO and CON arguments provided by the users "
                    + conflictData.userA.firstName + " " + conflictData.userA.lastName
                    + " and "
                    + conflictData.userB.firstName + " " + conflictData.userB.lastName
                    + ".";
                specificMessage = "There exist PRO and CON arguments for the same dimension \""
                    + attributeName + "\" provided by the users "
                    + conflictData.userA.firstName + " " + conflictData.userA.lastName
                    + " and "
                    + conflictData.userB.firstName + " " + conflictData.userB.lastName
                    + ".";
            }

            conflictMessages.push(specificMessage);
            attributeConflictMessages.push(message);
            attributeConflicts[conflictData.ratingAttributeID] = attributeConflictMessages;
        }

        var infoIconLink = $("<a></a>").addClass("or-rating-conflict-indicator")
            .attr("href", "javascript:void(0);")
            .attr("data-html", "true")
            .attr("data-toggle", "tooltip")
            .attr("data-placement", "bottom")
            .attr("title", conflictMessages.join("\n\n"));
        infoIconLink.append("<i class=\"material-icons\">flash_on</i>");
        inconsistencyLabel.html(infoIconLink);
        inconsistencyLabel.show();
        for (var attributeID in attributeConflicts) {
            var numOfConflicts = attributeConflicts[attributeID].length;
            var selector = $(".or-checkbox[data-dimension=" + attributeID + "]:last").parent().children(".argument-issues");
            var warningLink = infoIconLink.clone();
            warningLink.css("margin-left", "7px");
            warningLink.attr("title", attributeConflicts[attributeID].join("\n\n"));
            selector.append(warningLink);
        }
    }
    $('[data-toggle="tooltip"]').tooltip();
    if (refresh) {
        dataManager.showReleasesRequirementsAndIssues();
    }
}

var focusedLiSelector = null;

class DataManager {

	constructor(projectID, projectData, currentUserID, userData, uiManager) {
		this.ratingAttributeUrl = "/project/" + projectID + "/requirement/rating/attribute/list.json";
		this.stakeholderRatingAttributeUrl = "/project/" + projectID + "/requirement/stakeholder/rating/attribute/list.json";
		this.requirementUrl = "/project/" + projectID + "/requirement/list.json";
		this.releaseUrl = "/project/" + projectID + "/release/list.json";
		this.issueUrl = "/project/" + projectID + "/issue/list.json";
		this.consistencyCheckUrl = "/project/" + projectData.projectKey + "/checkconsistency.json";
		this.ambiguityIssueUrl = "https://api.openreq.eu/prs-improving-requirements-quality/check-quality";
		this.similarRequirementsRecommendationUrl = "/project/" + projectID + "/requirement/recommend/similar";
		this.projectData = projectData;
		this.currentUserID = currentUserID;
		this.ratingAttributeData = [];
        this.stakeholderRatingAttributeData = [];
		this.requirementData = [];
		this.releaseData = [];
		this.filteredReleaseData = [];
		this.dependencyData = [];
		this.issueData = [];
        this.consistencyIssueData = [];
        this.requirementMessages = {};
		this.userData = userData;
		this.uiManager = uiManager;

		this.newReleases = [];
		this.ignoredNewReleases = [];
		this.newRequirements = [];
		this.updatedRequirements = [];
		this.updatedReleases = [];
		this.deletedReleases = [];
		this.deletedRequirements = [];
		this.ambiguityIssueData = [];
	}

	getRequirementsMap() {
        var requirementsMap = {}
        for (var idx in this.requirementData) {
            var requirement = this.requirementData[idx];
            requirementsMap[requirement.id] = requirement;
        }
        return requirementsMap;
    }

    getRequirement(id) {
	    var requirementsMap = this.getRequirementsMap();
        return requirementsMap[id];
    }

	fetchRatingAttributes(callback) {
		$.getJSON(this.ratingAttributeUrl, function (data) {
			this.ratingAttributeData = data;
			callback(data);
		}.bind(this));
	}

	fetchStakeholderRatingAttributes(callback) {
		$.getJSON(this.stakeholderRatingAttributeUrl, function (data) {
			this.stakeholderRatingAttributeData = data;
			callback(data);
		}.bind(this));
	}

	fetchRequirements(callback) {
		$.getJSON(this.requirementUrl, function(data) {
			this.requirementData = data;
			callback(data);
		}.bind(this));
	}

	fetchReleases(callback) {
		$.getJSON(this.releaseUrl, function(data) {
			this.releaseData = data;
            var filterTextValue = $("#show-releases-filter").parent().children("input.select-dropdown").val();
            var valueMap = {
                "All releases": 0,
                "Only future releases": 1,
                "Only recent releases": 2,
                "Only past releases": 3
            };
            this.filterReleases(valueMap[filterTextValue]);
			callback();
		}.bind(this));
	}

    fetchIssues(callback) {
		$.getJSON(this.issueUrl, function(data) {
            this.issueData = data;
            $.getJSON(this.consistencyCheckUrl, function(data) {
                this.consistencyIssueData = data;
                callback();
            }.bind(this));
		}.bind(this));
	}

    fetchAmbiguityIssues(callback) {
	    if (this.projectData.projectSettings.readOnly) {
            callback();
	        return;
        }

	    /*
        var requirements = [];
	    for (var idx in this.requirementData) {
	        var r = this.requirementData[idx];
            var sentences = r.description.split(".");

            var elements = [];
            for (var idx in sentences) {
                var sentence = sentences[idx].trim();
                if (sentence.length == 0) {
                    continue;
                }

                var id = parseInt(idx) + 1;
                elements.push({
                    "id": id,
                    "name": "sentence " + id,
                    "text": sentence + ".",
                    "created_at": r.createdAt / 1000
                });
            }

            if (elements.length == 0) {
            	continue;
			}

            requirements.push({
                "id": r.id,
                "elements": elements,
                "status": r.status.toLowerCase()
            });
        }
        */

        var requirements = [];
	    for (var idx in this.requirementData) {
	        var r = this.requirementData[idx];
            var description = r.description.replace(/<\/?[^>]+(>|$)/g, "");
            if (description == "") {
                continue;
            }

            requirements.push({
                "id": r.id,
                "text": description
            });
        }

        if (requirements.length == 0) {
            callback();
	        return;
        }

        var jsonifiedString = JSON.stringify({ "requirements": requirements });
        $.ajax(this.ambiguityIssueUrl, {
            "data": jsonifiedString,
            "type": "POST",
            "contentType": "application/json",
            "processData": false,
            "success": function (data) {
                this.ambiguityIssueData = data;
                callback();
            }.bind(this),
            "error": function () {
            	this.ambiguityIssueData = [];
                callback();
			}.bind(this)
        });
	}

    fetchRecommendedSimilarRequirements(callback) {
		$.getJSON(this.similarRequirementsRecommendationUrl, function(data) {
			this.similarRequirementsData = data;
			callback();
		}.bind(this)).error(function() {
            callback();
        }.bind(this));
	}

    createDependency(projectID, rx, ry, type, callback) {
	    rx = parseInt(rx);
	    ry = parseInt(ry);

        var jsonifiedString = JSON.stringify({
            sourceRequirementID: rx,
            targetRequirementID: ry,
            type: type
        });

        var rxRequirement = this.getRequirement(rx);
        var ryRequirement = this.getRequirement(ry);

        $.ajax("/project/" + projectID + "/dependency/create.json", {
            'data': jsonifiedString,
            'type': 'POST',
            'contentType': 'application/json',
            'processData': false,
            'success': function (result) {
                this.dependencyData.push({
                    sourceRequirementID: rx,
                    sourceRequirementTitle: rxRequirement.title,
                    sourceRequirementDescription: rxRequirement.description,
                    targetRequirementID: ry,
                    targetRequirementTitle: ryRequirement.title,
                    targetRequirementDescription: ryRequirement.description,
                    type: type
                });
                callback(result);
            }.bind(this)
        });
    }

    hasChanges(allowAlerts) {
		var collectedChanges = this.collectChanges(allowAlerts);
        return (("projectName" in collectedChanges.updatedProjectData)
            || ("projectDescription" in collectedChanges.updatedProjectData)
            || (collectedChanges.updatedRequirements.length > 0)
            || (collectedChanges.newRequirements.length > 0)
            || (collectedChanges.updatedReleases.length > 0)
            || (collectedChanges.newReleases.length > 0)
            || (collectedChanges.deletedReleases.length > 0)
            || (collectedChanges.deletedRequirements.length > 0)
		);
	}

	collectChanges(allowAlerts) {
		var updatedProjectData = {};

		if (this.projectData.projectName != $("#or-project-name").val()) {
			updatedProjectData["projectName"] = $("#or-project-name").val();
		}

		if (this.projectData.projectDescription != $("#or-project-description").val()) {
			updatedProjectData["projectDescription"] = $("#or-project-description").val();
		}

		this.newReleases = [];
		this.ignoredNewReleases = [];
		this.ignoredNewReleaseNumbers = [];
		this.newRequirements = [];
		this.updatedRequirements = [];
		this.updatedReleases = [];
		this.deletedReleases = [];
		this.deletedRequirements = [];

		var filteredReleaseIDs = [];
		for (var i in this.filteredReleaseData) {
			this.filteredReleaseData[i].isStillPresent = false;
            filteredReleaseIDs.push(this.filteredReleaseData[i].id);
		}

		for (var i in this.requirementData) {
		    if ((this.requirementData[i].releaseID > 0) && filteredReleaseIDs.indexOf(this.requirementData[i].releaseID) == -1) {
                this.requirementData[i].isStillPresent = true;
            } else {
                this.requirementData[i].isStillPresent = false;
            }
		}

		$(".or-project-requirement-table").children("tbody")
            .children("tr.or-requirement")
            .each(bindEach(this, "handleRequirementCheck", allowAlerts));

		$(".or-release").each(bindEach(this, "handleReleaseCheck", allowAlerts));

		for (var i in this.filteredReleaseData) {
			if (!this.filteredReleaseData[i].isStillPresent) {
				this.deletedReleases.push(this.releaseData[i]);
			}
		}

		for (var i in this.requirementData) {
			if (!this.requirementData[i].isStillPresent) {
				this.deletedRequirements.push(this.requirementData[i]);
			}
		}

		return {
			newReleases: this.newReleases,
			ignoredNewReleases: this.ignoredNewReleases,
			newRequirements: this.newRequirements,
			updatedProjectData: updatedProjectData,
			updatedRequirements: this.updatedRequirements,
			updatedReleases: this.updatedReleases,
			deletedReleases: this.deletedReleases,
			deletedRequirements: this.deletedRequirements,
			ignoredNewReleaseNumbers: this.ignoredNewReleaseNumbers
		};
	}

	handleRequirementCheck(reference, allowAlerts) {
		var requirementIDExists = $(reference).is("[id]");
		var releaseIDString = $(reference).parent("tbody").parent(".or-project-requirement-table").attr("id");
		var assignedRelease = (releaseIDString != "or-project-unassigned-requirements") ? releaseIDString : null;
		var requirementTitleSelector = $(reference).children("td").children(".or-requirement-title");
		var importID = requirementTitleSelector.attr("data-import-id");
		var requirementTitle = requirementTitleSelector.val();
		var requirementDescription = $(reference).children("td").children("div.or-requirement-description").summernote("code");
		var requirementStatus = $(reference).children("td.or-requirement-status").children("div").children("input.select-dropdown").val().toUpperCase();
		requirementStatus = requirementStatus.replace(" ", "");
        var requirementDescriptionStripped = requirementDescription.replace(/<\/?[^>]+(>|$)/g, "");

		if (requirementIDExists) {
			var requirementID = parseInt($(reference).attr("id").split("-")[2]);
			for (var i in this.requirementData) {
				var existingRequirementData = this.requirementData[i];
				if (existingRequirementData.id != requirementID) {
					continue;
				}

				this.requirementData[i].isStillPresent = true;

				var currentlyAssignedRelease = (existingRequirementData.releaseID > 0) ? "or-table-release-" + existingRequirementData.releaseID : null;
				if ((existingRequirementData.title != requirementTitle) || (existingRequirementData.description != requirementDescription)
                    || (currentlyAssignedRelease != assignedRelease) || (existingRequirementData.status != requirementStatus)) {
					this.updatedRequirements.push({
                        id: requirementID,
                        title: requirementTitle,
                        description: requirementDescription,
                        importID: importID,
                        assignedRelease: assignedRelease,
                        status: requirementStatus
					});
				}
			}
		} else {
			if ((requirementTitle != "") || (requirementDescriptionStripped != "")) {
				this.newRequirements.push({
                    title: requirementTitle,
                    description: requirementDescription,
                    importID: importID,
                    assignedRelease: assignedRelease,
                    status: requirementStatus
				});
			}
			if ((requirementTitle == "") && (requirementDescriptionStripped != "") && allowAlerts) {
				alert("Please enter a valid title for the requirement!");
				requirementTitleSelector.focus();
			}
		}
	}

	handleReleaseCheck(reference, allowAlerts) {
		var releaseTitleSelector = $(reference).children(".collapsible-header")
                                               .children(".or-release-title-row")
                                               .children(".or-form-edit-release-description-col")
                                               .children(".or-form-release-title-field");
        var releaseCapacitySelector = $(reference).children(".collapsible-header")
                                                  .children(".or-release-title-row")
                                                  .children(".or-form-edit-release-capacity-col")
                                                  .children(".or-form-release-capacity-field");
		var releaseDateColumn = $(reference).children(".collapsible-header")
                                            .children(".or-release-title-row")
                                            .children(".or-form-edit-release-date-col");
		var releaseTitle = releaseTitleSelector.val();
		var releaseDescription = $(reference).children(".collapsible-body")
                                             .children("p")
                                             .children("div.row")
                                             .children("div.or-form-edit-release-description-col")
                                             .children(".or-form-release-description-field")
                                             .val();
        var releaseCapacity = parseInt(releaseCapacitySelector.val());
		var releaseEndDateParts = releaseDateColumn.children(".or-form-release-end-date-field").val().split("-");
        var releaseEndDate = new Date(releaseEndDateParts[0], (Number(releaseEndDateParts[1]) - 1), releaseEndDateParts[2], 0, 0, 0, 0);
		var releaseID = parseInt($(reference).attr("id").split("-")[2]);

		if ($(reference).attr("id").split("-")[1] != "newrelease") {
			for (var i in this.releaseData) {
				var existingReleaseData = this.releaseData[i];
				if (existingReleaseData.id != releaseID) {
					continue;
				}

				this.releaseData[i].isStillPresent = true;

				if ((existingReleaseData.title != releaseTitle) ||
                    (existingReleaseData.description != releaseDescription) ||
                    (existingReleaseData.capacity != releaseCapacity) ||
                    (existingReleaseData.endDateTimestamp != releaseEndDate.getTime()))
				{
					this.updatedReleases.push({
                        id: releaseID,
                        name: releaseTitle,
                        description: releaseDescription,
                        capacity: releaseCapacity,
                        endDateTimestamp: releaseEndDate.getTime()
					});
				}
			}
		} else {
			releaseTitle = (releaseTitle != "") ? releaseTitle : "Unnamed release";
			if ((releaseTitle != "") || (releaseDescription != "")) {
				this.newReleases.push({
                    name: releaseTitle,
                    description: releaseDescription,
                    capacity: releaseCapacity,
                    newReleaseID: releaseID,
                    endDateTimestamp: releaseEndDate.getTime()
				});
			} else {
				this.ignoredNewReleases.push({
                    name: releaseTitle,
                    description: releaseDescription,
                    capacity: releaseCapacity,
                    newReleaseID: releaseID,
                    endDateTimestamp: releaseEndDate.getTime()
				});
				this.ignoredNewReleaseNumbers.push(releaseID);
			}

			if ((releaseTitle == "") && (releaseDescription != "") && allowAlerts) {
				alert("Please enter a valid title for the release!");
				releaseTitleSelector.focus();
			}
		}
	}

    filterReleases(filterValue) {
        var now = new Date();
        if (filterValue == 0) {
            this.filteredReleaseData = this.releaseData;
        } else if (filterValue == 1) {
            this.filteredReleaseData = this.releaseData.filter(r => r.endDateTimestamp > now.getTime());
        } else if (filterValue == 2) {
            var oneMonthAgo = new Date();
            var threeMonthsLater = new Date();
            oneMonthAgo.setMonth(oneMonthAgo.getMonth()-1);
            threeMonthsLater.setMonth(threeMonthsLater.getMonth()+3);
            this.filteredReleaseData = this
                .releaseData
                .filter(r => r.endDateTimestamp > oneMonthAgo.getTime() && r.endDateTimestamp < threeMonthsLater.getTime());
        } else {
            this.filteredReleaseData = this.releaseData.filter(r => r.endDateTimestamp < now.getTime());
        }
    }

    showReleasesRequirementsAndIssues() {
		$(".or-requirement").remove();
		$(".or-release").remove();
		$("#or-no-release-found-placeholder").show();
		var sortedRequirements = [];

        for (var i in this.requirementData) {
            var requirementD = this.requirementData[i];
            var isPrivateProject = this.projectData.isPrivateProject;
            var evaluationMode = this.projectData.projectSettings.evaluationMode;
            var utility = 0.0;

            if (evaluationMode == "BASIC") {
                var userBasicRatings = requirementD.userBasicRatings;
                utility = computeAverageRating(userBasicRatings);
            } else if (evaluationMode == "NORMAL") {
                var anonymousRatings = requirementD.anonymousRatings;
                var userRatings = requirementD.userRatings;
                var ratings = (isPrivateProject ? userRatings : anonymousRatings);
                utility = computeMAUT(this.ratingAttributeData, ratings,
                    requirementD.ratingForwardDelegations, requirementD.ratingBackwardDelegations);
            } else if (evaluationMode == "ADVANCED") {
                var requirementMessages = requirementD.messages;
                var ratings = convertMessagesToRatings(this.ratingAttributeData, requirementMessages);
                utility = computeMAUT(this.ratingAttributeData, ratings, {}, {});
            }

            sortedRequirements.push({
                data: requirementD,
                utility: utility
            });
        }

        this.requirementData = sortedRequirements.sort(function (a, b) {
            return b.utility - a.utility;
        }).map(e => e.data);

        for (var i in this.requirementData) {
			var requirementD = this.requirementData[i];
			if (requirementD.releaseID > 0) {
				continue;
			}
			this.uiManager.addRequirementFormFields(
				$("#or-project-unassigned-requirements"),
				requirementD,
				this.ratingAttributeData,
				false,
				false,
				true
			);
		}

		for (var i in this.filteredReleaseData) {
			var releaseD = this.filteredReleaseData[i];
			var releaseTableSelector = this.uiManager.addRelease(
					releaseD.id,
					releaseD.title,
					releaseD.description,
					releaseD.capacity,
					releaseD.endDateTimestamp,
					releaseD.humanFriendlyEndDate,
					false
			);
			for (var i in this.requirementData) {
				var requirementD = this.requirementData[i];
				if (requirementD.releaseID != releaseD.id) {
					continue;
				}
				this.uiManager.addRequirementFormFields(
					releaseTableSelector,
					requirementD,
					this.ratingAttributeData,
                    false,
					false,
					true
				);
			}
		}

        var filteredRequirementData = [];
        for (var i in this.releaseData) {
			var releaseD = this.releaseData[i];
			for (var i in this.requirementData) {
				var requirementD = this.requirementData[i];
				if (requirementD.releaseID != releaseD.id) {
					continue;
				}
				filteredRequirementData.push(requirementD);
			}
		}

		for (var i in this.requirementData) {
			var requirementD = this.requirementData[i];
			if (requirementD.releaseID == 0) {
				filteredRequirementData.push(requirementD);
			}
		}

		this.requirementData = filteredRequirementData;
	    $("table.or-project-requirement-table").not("#or-project-unassigned-requirements").each(function (index, tableSelector) {
	    		this.uiManager.addRequirementFormFields($(tableSelector), null, this.ratingAttributeData, false, false, true);
	    	}.bind(this));
	    $("table#or-project-unassigned-requirements").each(function (index, tableSelector) {
			if ($(tableSelector).children("tbody").children("tr").length == 0) {
				this.uiManager.addRequirementFormFields($(tableSelector), null, this.ratingAttributeData, false, true, true);
			}
	    }.bind(this));

		this.uiManager.uiEventHandler.bindUIEvents();
		this.uiManager.resetReleaseCounter();
        this.uiManager.showAmbiguityIssues();
		if (!this.projectData.projectSettings.readOnly) {
            this.uiManager.showIssues();
            this.uiManager.showSimilarRequirementRecommendations();
        } else {
		    $("#or-notification-button-container").hide();
        }
        this.uiManager.showVisiblePanelsAndViews();
		this.uiManager.showUnsavedChangesNotificationIfNecessary();

        var requirementIconContainerSelectorMap = {};
        for (var idx in this.requirementData) {
            var rData = this.requirementData[idx];
            var requirementIconContainerSelector = $("#or-requirement-" + rData.id + " > .or-requirement-icon-container");
            requirementIconContainerSelectorMap[rData.id] = requirementIconContainerSelector;
        }

		this.uiManager.notificationCenter.setRequirementIconContainerSelectorMap(requirementIconContainerSelectorMap);
        $('[data-toggle="tooltip"]').tooltip();
        if (this.projectData.projectSettings.readOnly) {
            $(".or-project-requirement-table")
                .not("#or-project-unassigned-requirements")
                .children("tbody")
                .children(".or-requirement")
                .each(function () {
                    var htmlID = $(this).attr("id");
                    var hasHTMLAttributeID = (typeof htmlID !== typeof undefined && htmlID !== false);
                    if (!hasHTMLAttributeID) {
                        $(this).remove();
                    }
                });
            $(".or-project-requirement-table").each(function () {
                if ($(this).children("tbody").children(".or-requirement").length == 0) {
                    var releaseID = parseInt($(this).attr("id").split("-")[3]);
                    $(this).hide();
                    $(".or-release-empty-" + releaseID).show();
                }
            });
        }
    }

	fetchAndShowRequirementsAndReleases(completeCallback) {
        // updating the projectData necessary for refresh!
		this.projectData = {
			projectID: this.projectData.projectID,
			projectKey: this.projectData.projectKey,
			projectName: $("#or-project-name").val(),
			projectDescription: $("#or-project-description").val(),
            creatorUserID: this.projectData.creatorUserID,
            isPrivateProject: this.projectData.isPrivateProject,
            projectSettings: this.projectData.projectSettings
		};

		this.fetchRatingAttributes(function () {
		    var callbackChain = function () {
                this.fetchRequirements(function () {
                    this.fetchReleases(function () {
                        this.fetchIssues(function () {
                            this.fetchRecommendedSimilarRequirements(function () {
                                this.fetchAmbiguityIssues(function () {
                                    this.showReleasesRequirementsAndIssues();
                                    if (completeCallback !== undefined) {
                                        completeCallback();
                                    }
                                }.bind(this));
                            }.bind(this));
                        }.bind(this));
                    }.bind(this));
                }.bind(this));
            }.bind(this);

		    if (this.projectData.isPrivateProject) {
                this.fetchStakeholderRatingAttributes(callbackChain);
            } else {
		        callbackChain();
            }
		}.bind(this));
	}

}

var UINotificationType = {
    GLOBAL: 1,
    ISSUE: 2,
    SERVICE_ISSUE: 3,
    SERVICE_NOTIFICATION: 4,
    REQUIREMENT: 5,
    RELEASE: 6,
    DEPENDENCY: 7
};

var UINotificationTag = {
    REQUIREMENT_SIMILARITY: 1,
    CONSISTENCY_CHECK: 2,
    ISSUE: 3
};

var NotificationPriority = {
    CRITICAL: 1,
    HIGH: 2,
    NORMAL: 3,
    LOW: 4
};

class UINotification {

    constructor(id, type, title, description, tags, info) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.tags = tags !== undefined ? tags : [];
        this.info = info !== undefined ? info : {}
    }

    hasTag(tag) {
        for (var idx in this.tags) {
            if (tag == this.tags[idx]) {
                return true;
            }
        }
        return false;
    }

}

class UINotificationCenter {

    constructor(notificationButtonContainerSelector, uiEventHandler) {
        console.log("[UINotificationCenter] constructor()");
        this.notifications = {};
        this.requirementIconContainerSelectorMap = {};
        this.messageCounter = 0;
        this.uiEventHandler = uiEventHandler;
    }

    setRequirementIconContainerSelectorMap(requirementIconContainerSelectorMap) {
        console.log("[UINotificationCenter] setRequirementIconContainerSelectorMap()");
        this.requirementIconContainerSelectorMap = requirementIconContainerSelectorMap;
    }

    addNotification(notification) {
        console.log("[UINotificationCenter] addNotification()");
        if (notification.id in this.notifications) {
            return;
        }
        this.notifications[notification.id] = notification;
    }

    clearAllNotificationsWithTag(tag) {
        var remainingNotifications = {};
        for (var notificationID in this.notifications) {
            var notification = this.notifications[notificationID];
            if (!notification.hasTag(tag)) {
                remainingNotifications[notificationID] = notification;
            }
        }
        this.notifications = remainingNotifications;
    }

    clear() {
        this.notifications = {};
    }

    render() {
        console.log("[UINotificationCenter] render()");

        var requirementNotificationMessages = {};
        var releaseNotificationMessages = {};
        var dependencyNotificationMessages = {};

        $("#or-project-issues > tbody").children().remove();
        var notificationIDs = {};

        for (var notificationID in this.notifications) {
            var notification = this.notifications[notificationID];
            if (notification.type == UINotificationType.GLOBAL) {
                notificationIDs[notificationID] = NotificationPriority.NORMAL;
            } else if (notification.type == UINotificationType.ISSUE) {
                notificationIDs[notificationID] = NotificationPriority[notification.info.priority];
            } else if (notification.type == UINotificationType.SERVICE_ISSUE) {
                notificationIDs[notificationID] = NotificationPriority.CRITICAL;
            } else if (notification.type == UINotificationType.SERVICE_NOTIFICATION) {
                notificationIDs[notificationID] = NotificationPriority.NORMAL;
            } else if (notification.type == UINotificationType.REQUIREMENT) {
                notificationIDs[notificationID] = NotificationPriority.NORMAL;
            } else if (notification.type == UINotificationType.DEPENDENCY) {
                notificationIDs[notificationID] = NotificationPriority.NORMAL;
            } else if (notification.type == UINotificationType.RELEASE) {
                notificationIDs[notificationID] = NotificationPriority.NORMAL;
            }
        }

        this.messageCounter = 0;
        var notificationCenter = this;
        var uiEventHandler = this.uiEventHandler;

        bySortedValue(notificationIDs, function(notificationID, priority) {
            var notification = notificationCenter.notifications[notificationID];
            ++notificationCenter.messageCounter;

            if (notification.type == UINotificationType.GLOBAL) {
                uiEventHandler.addIssueRow("Issue", notification.description, notificationCenter.messageCounter,
                    priority, null);
            } else if (notification.type == UINotificationType.ISSUE) {
                var issueData = notification.info;
                uiEventHandler.addIssueRow(notification.title, notification.description,
                    notificationCenter.messageCounter, priority, null, issueData.id);

            } else if (notification.type == UINotificationType.SERVICE_ISSUE) {
                var title = "Service failure" + (notification.hasTag() ? " " + notification.tags[0] : "");
                uiEventHandler.addIssueRow(title, notification.description, notificationCenter.messageCounter,
                    priority, null);

            } else if (notification.type == UINotificationType.SERVICE_NOTIFICATION) {
                var title = "Service Notification" + (notification.hasTag() ? " " + notification.tags[0] : "");
                uiEventHandler.addIssueRow(title, notification.description, notificationCenter.messageCounter,
                    priority, null);

            } else if (notification.type == UINotificationType.REQUIREMENT) {
                var requirementID = notification.info.requirementID;
                if (!(requirementID in requirementNotificationMessages)) {
                    requirementNotificationMessages[requirementID] = [];
                }
                requirementNotificationMessages[requirementID].push(notification.description);

                var title = "Similar requirements";
                uiEventHandler.addIssueRow(title, notification.description, notificationCenter.messageCounter,
                    priority, null);

            } else if (notification.type == UINotificationType.DEPENDENCY) {
                var requirementIDX = notification.info.rx;
                var requirementIDY = notification.info.ry;
                var dependencyType = notification.info.type;
                var dependencyHTMLID = "or-dependency-" + dependencyType + "-" + requirementIDX + "-" + requirementIDY;

                if (!(requirementIDX in requirementNotificationMessages)) {
                    requirementNotificationMessages[requirementIDX] = [];
                }

                if (!(requirementIDY in requirementNotificationMessages)) {
                    requirementNotificationMessages[requirementIDY] = [];
                }

                if (!(dependencyHTMLID in dependencyNotificationMessages)) {
                    dependencyNotificationMessages[dependencyHTMLID] = [];
                }

                requirementNotificationMessages[requirementIDX].push(notification.description);
                requirementNotificationMessages[requirementIDY].push(notification.description);
                dependencyNotificationMessages[dependencyHTMLID].push(notification.description);

                var title = "Requirement interdependency";
                uiEventHandler.addIssueRow(title, notification.description, notificationCenter.messageCounter,
                    priority, null);

            } else if (notification.type == UINotificationType.RELEASE) {
                var releaseID = notification.info.releaseID;
                if (!(releaseID in releaseNotificationMessages)) {
                    releaseNotificationMessages[releaseID] = [];
                }
                releaseNotificationMessages[releaseID] = notification.description;

                var title = "Release notification";
                uiEventHandler.addIssueRow(title, notification.description, notificationCenter.messageCounter,
                    priority, null);
            } else {
                --notificationCenter.messageCounter;
            }
        });

        if (this.messageCounter > 0) {
            $("#or-notification-button-container > .or-badge").text(this.messageCounter).show();
        } else {
            $("#or-notification-button-container > .or-badge").hide();
        }

        // show global notifications
        $("#or-notification-button-container").hide();
        $("#or-notification-button-container").attr("style", "display: inline-block !important");

        // show requirement notifications
        for (var requirementID in requirementNotificationMessages) {
            var notificationMessages = requirementNotificationMessages[requirementID];
            var requirementIconContainer = $("#or-requirement-" + requirementID + " .or-requirement-icon-container");
            requirementIconContainer.children(".or-badge").remove();
            var icon = requirementIconContainer.children(".or-requirement-icon");
            icon.attr("data-toggle", "tooltip");
            icon.attr("data-placement", "bottom");
            icon.attr("data-html", "true");
            icon.attr("style", "cursor:hand;");
            icon.attr("title", notificationMessages.join("\n"));
            requirementIconContainer.attr("data-issue-counter", notificationMessages.length);
            var issueCounterLabel = $("<span></span>")
                .addClass("or-badge or-badge-requirement-icon")
                .text(notificationMessages.length);
            requirementIconContainer.append(issueCounterLabel);
        }

        // show release notifications
        for (var releaseID in releaseNotificationMessages) {
            var notificationMessages = releaseNotificationMessages[releaseID];
            // TODO: implement this!
            /*
            var releaseIconContainer = $("#or-requirement-" + releaseID + " .or-requirement-icon-container");
            releaseIconContainer.children(".or-badge").remove();
            var icon = releaseIconContainer.children(".or-requirement-icon");
            icon.attr("data-toggle", "tooltip");
            icon.attr("data-placement", "bottom");
            icon.attr("style", "cursor:hand;");
            icon.attr("title", notificationMessages.join("\n"));
            releaseIconContainer.attr("data-issue-counter", notificationMessages.length);
            var issueCounterLabel = $("<span></span>")
                .addClass("or-badge or-badge-requirement-icon")
                .text(notificationMessages.length);
            releaseIconContainer.append(issueCounterLabel);
            */
        }

        // show dependency notifications
        for (var dependencyHTMLID in dependencyNotificationMessages) {
            var notificationMessages = dependencyNotificationMessages[dependencyHTMLID];
            var dependencyIconContainer = $("#" + dependencyHTMLID + " > .or-dependency-middle");
            dependencyIconContainer.children(".or-badge").remove();
            dependencyIconContainer.attr("data-toggle", "tooltip");
            dependencyIconContainer.attr("data-placement", "bottom");
            dependencyIconContainer.attr("style", "cursor:hand;");
            dependencyIconContainer.attr("title", notificationMessages.join("\n"));
            dependencyIconContainer.attr("data-issue-counter", notificationMessages.length);
            var issueCounterLabel = $("<span></span>")
                .addClass("or-badge or-badge-dependency-icon")
                .text(notificationMessages.length);
            dependencyIconContainer.append(issueCounterLabel);
        }

        $('[data-toggle="tooltip"]').tooltip();
    }

}

class UIManager {

	constructor(projectID, projectKey, uiEventHandler) {
		this.projectID = projectID;
		this.projectKey = projectKey;
		this.dataManager = null;
		this.uiEventHandler = uiEventHandler;
		this.uiEventHandler.uiManager = this;
		this.saveHandler = new SaveHandler(this.projectID);
		this.cookieHandler = new CookieHandler("explanationShown", 365);
		this.newReleaseCounter = 0;
		this.notificationCenter = new UINotificationCenter($(".or-notification-button-container"), uiEventHandler);
	}

	resetReleaseCounter() {
		this.newReleaseCounter = 0;
	}

	initialize() {
		this.uiEventHandler.prepare();
        $("#or-tab-navigation-bar").hide();
        this.hideUnsavedChangesNotificationBar();
    }

    showVisiblePanelsAndViews() {
	    var projectSettings = this.dataManager.projectData.projectSettings;

	    if (projectSettings.showDependencies) {
            $(".or-tab-dependencies").show();
        } else {
	        $(".or-tab-dependencies").hide();
        }

	    if (projectSettings.showStatistics) {
            $(".or-tab-statistics").show();
        } else {
            $(".or-tab-statistics").hide();
        }

        if (projectSettings.showSocialPopularityIndicator) {
            $(".or-requirement-social-popularity").show();
        } else {
            $(".or-requirement-social-popularity").hide();
        }

        if (projectSettings.showStakeholderAssignment) {
            $(".or-requirement-assigned-stakeholder").show();
        } else {
            $(".or-requirement-assigned-stakeholder").hide();
        }

        if (projectSettings.showAmbiguityAnalysis) {
            $(".or-requirement-quality-link").show();
        } else {
            $(".or-requirement-quality-link").hide();
            $(".note-editor").removeClass("or-requirement-ambiguity-highlight");
        }

        $(".or-requirement-basic-evaluation").hide();
        $(".or-requirement-normal-evaluation").hide();
        $(".or-requirement-advanced-evaluation").hide();

        if (projectSettings.evaluationMode == "BASIC") {
            $(".or-requirement-basic-evaluation").show();
        } else if (projectSettings.evaluationMode == "NORMAL") {
            $(".or-requirement-normal-evaluation").show();
        } else if (projectSettings.evaluationMode == "ADVANCED") {
            $(".or-requirement-advanced-evaluation").show();
        }

        if (!projectSettings.showDependencies && !projectSettings.showStatistics) {
            $(".or-navbar, .maincontainer").addClass("or-no-tab-navigation-bar");
	        $("#or-tab-navigation-bar").hide();
        } else {
            $(".or-navbar, .maincontainer").removeClass("or-no-tab-navigation-bar");
            $("#or-tab-navigation-bar").fadeIn();
		}
    }

    showIssues() {
        var issues = this.dataManager.issueData["issues"];
        this.notificationCenter.clearAllNotificationsWithTag(UINotificationTag.ISSUE);
        for (var i in issues) {
            var issueData = issues[i];
            var notificationID = "or-issue-" + i;
            var notification = new UINotification(notificationID, UINotificationType.ISSUE,
                issueData.title, issueData.description, [UINotificationTag.ISSUE], issueData);
            this.notificationCenter.addNotification(notification);
        }
        this.showConsistencyIssues();
    }

    showConsistencyIssues() {
	    var issueData = this.dataManager.consistencyIssueData;
        var requirements = this.dataManager.requirementData;
        var requirementsMap = {}
        for (var idx in requirements) {
            var requirement = requirements[idx];
            requirementsMap[requirement.id] = requirement;
        }

        this.notificationCenter.clearAllNotificationsWithTag(UINotificationTag.CONSISTENCY_CHECK);
        $(".or-consistency-check-message").hide();

	    if (issueData.error) {
            var notificationID = "or-global-consistency-error";
            var notification = new UINotification(notificationID, UINotificationType.SERVICE_ISSUE,
                "Consistency check error", "The consistency check service is not available! " +
                "Please contact the administrator of this system.", [UINotificationTag.CONSISTENCY_CHECK]);
            this.notificationCenter.addNotification(notification);

            if (this.dataManager.dependencyData.length > 0) {
                $(".or-consistency-check-service-unavailable-message").fadeIn();
            }
        } else if (!issueData.consistent) {
            var notificationID = "or-global-consistency-error";
            var notification = new UINotification(notificationID, UINotificationType.SERVICE_ISSUE,
                "Data model inconsistent", "The data model is inconsistent! Please check all " +
                "dependencies between the requirements and the effort estimation of the requirements with respect " +
                "to the maximum capacity of the corresponding release.", [UINotificationTag.CONSISTENCY_CHECK]);
            this.notificationCenter.addNotification(notification);

            for (var idx in issueData.diagnosisConstraints) {
                var constraint = issueData.diagnosisConstraints[idx];
                var notification = null;

                if (constraint.type == "GREATER_EQUAL_THAN") {
                    var rx = constraint.rx;
                    var ry = constraint.ry;
                    var rxRequirement = requirementsMap[rx];
                    var ryRequirement = requirementsMap[ry];

                    //title += "There are inconsistencies, issues with requirement <requirements> take a look at dependency between rx and ry, ry and rz ...";
                    var notificationID = "or-req-diag-gte-" + rx + "-" + ry;
                    notification = new UINotification(notificationID, UINotificationType.DEPENDENCY,
                        //"This dependency is (probably a.o.) responsible for an inconsistency " +
                        //"in the requirements model. Please take a detailed look at this one.
                        "Constraint inconsistency", "There is an inconsistency issue between requirement \""
                        + rxRequirement.title + "\" (#" + rxRequirement.projectSpecificRequirementId + ") and requirement \""
                        + ryRequirement.title + "\" (#" + ryRequirement.projectSpecificRequirementId +"). "
                        + "Please take a look at the dependency between #" + rxRequirement.projectSpecificRequirementId
                        + " and #" + ryRequirement.projectSpecificRequirementId + ".", [UINotificationTag.CONSISTENCY_CHECK],
                        { rx: rx, ry: ry, type: "REQUIRES" });
                } else if (constraint.type == "EXCLUDES") {
                    var rx = constraint.rx;
                    var ry = constraint.ry;

                    var notificationID = "or-req-diag-exc-" + rx + "-" + ry;
                    notification = new UINotification(notificationID, UINotificationType.DEPENDENCY,
                        "Constraint inconsistency", "There is an inconsistency issue with requirement " + ry
                        + " take a look at the dependency between " + rx + " and " + ry + ".", [UINotificationTag.CONSISTENCY_CHECK],
                        { rx: rx, ry: ry, type: "EXCLUDES" });
                } else if (constraint.type == "SUM") {
                    var releaseID = constraint.meta.releaseID;
                    var notificationID = "or-req-diag-sum-" + releaseID;
                    notification = new UINotification(notificationID, UINotificationType.RELEASE,
                        "Constraint inconsistency", "There is an inconsistency issue. The overall effort of all " +
                        "requirements in release " + releaseID +" exceeds the maximum capacity of this release.",
                        [UINotificationTag.CONSISTENCY_CHECK], { releaseID: releaseID });
                } else {
                    console.log("Error: Unhandled constraint type \"" + constraint.type + "\"");
                    continue;
                }

                this.notificationCenter.addNotification(notification);
            }

            if (this.dataManager.dependencyData.length > 0) {
                $(".or-consistency-check-error-message").fadeIn();
            }
        } else if (issueData.consistent) {
            var notificationID = "or-global-consistency-success";
            var notification = new UINotification(notificationID, UINotificationType.SERVICE_NOTIFICATION,
                "Data model inconsistent", "Congratulations! The data model is consistent.",
                [UINotificationTag.CONSISTENCY_CHECK]);
            this.notificationCenter.addNotification(notification);
            if (this.dataManager.dependencyData.length > 0) {
                $(".or-consistency-check-success-message").fadeIn();
            }
		}

        this.notificationCenter.render();
    }

    showAmbiguityIssues() {
        var ambiguityIssueData = this.dataManager.ambiguityIssueData;
        console.log(ambiguityIssueData);
        for (var requirementID in ambiguityIssueData) {
            var requirementIssues = ambiguityIssueData[requirementID];
            var numberOfIssues = requirementIssues.length;
            if (numberOfIssues > 0) {
                var qualityIcon = $("<span></span>")
                    .addClass("or-badge or-badge-requirement-quality-icon or-requirement-quality-link")
                    .attr("data-requirement-id", requirementID)
                    .attr("data-toggle", "tooltip")
                    .attr("data-placement", "bottom")
                    .attr("title", "Click to show textual ambiguities")
                    .text(numberOfIssues);
                $("#or-requirement-" + requirementID + " > td.or-requirement-description > .note-editor")
                    .addClass("or-requirement-ambiguity-highlight")
                    .prepend(qualityIcon);
            }
        }
        this.uiEventHandler.bindUIEvents();
    }

    showSimilarRequirementRecommendations() {
        var requirements = this.dataManager.requirementData;
        var requirementsMap = {};
        this.notificationCenter.clearAllNotificationsWithTag(UINotificationTag.REQUIREMENT_SIMILARITY);

        for (var idx in requirements) {
            var requirement = requirements[idx];
            requirementsMap[requirement.id] = requirement;
        }

        var similarRequirementsData = this.dataManager.similarRequirementsData;
        var projectSpecificRequirementIDMap = {};
        for (var i in similarRequirementsData) {
            var subjectRequirementData = similarRequirementsData[i];
            projectSpecificRequirementIDMap[subjectRequirementData.id] = subjectRequirementData.projectSpecificRequirementId;
        }

        for (var i in similarRequirementsData) {
            var subjectRequirementData = similarRequirementsData[i];
            if (subjectRequirementData.predictions.length == 0) {
                continue;
            }

            var similarRequirementTitles = [];

            for (var j in subjectRequirementData.predictions) {
                var targetRequirementID = subjectRequirementData.predictions[j];
                var projectSpecificRequirementID = projectSpecificRequirementIDMap[targetRequirementID];
                var similarRequirementTitle = "\"" + requirementsMap[targetRequirementID].title
                    + "\" (#" + projectSpecificRequirementID + ")";
                similarRequirementTitles.push(similarRequirementTitle);
            }

            var notificationID = "or-req-sim-" + subjectRequirementData.id;
            var notification = new UINotification(notificationID, UINotificationType.REQUIREMENT,
                "Requirement Similarity", "Requirement \"" + requirementsMap[subjectRequirementData.id].title
                + "\" (#" + subjectRequirementData.projectSpecificRequirementId +
                ") is similar to " + similarRequirementTitles.join(", "), [UINotificationTag.REQUIREMENT_SIMILARITY],
                { requirementID: subjectRequirementData.id });
            this.notificationCenter.addNotification(notification);
        }
        this.notificationCenter.render();
    }

	showUnsavedChangesNotificationIfNecessary() {
		console.log("Check for unsaved changes!");
		if (this.dataManager.hasChanges(false)) {
			$(window).on("beforeunload", function(){
                return "Are you sure you want to leave this page? Changes that you made may not be saved.";
	         });
			$("#or-unsaved-changes-notification-bar").fadeIn();
			$(".maincontainer").addClass("or-notification-bar-offset");
		} else {
		    this.hideUnsavedChangesNotificationBar();
		}
	}

	hideUnsavedChangesNotificationBar() {
        $("#or-unsaved-changes-notification-bar").fadeOut();
        $(window).off("beforeunload");
        $(".maincontainer").removeClass("or-notification-bar-offset");
    }

	showExplanationIfNecessary(tapTargetSelector) {
		if (this.cookieHandler.getValue() != "") { return; }
		tapTargetSelector.tapTarget('open');
		window.setTimeout(function () {
			tapTargetSelector.tapTarget('close');
		}.bind(tapTargetSelector), 2000);
		this.cookieHandler.setValue("true");
	}

	updateVisibilityStatus(isPrivateProject) {
		if (isPrivateProject) {
			$("#or-visibility-button-private-container").show();
			$("#or-visibility-button-public-container").hide();
		} else {
			$("#or-visibility-button-private-container").hide();
			$("#or-visibility-button-public-container").show();
		}
	}

	addRelease(releaseID, releaseName, releaseDescription, capacity, endDateTimestamp, humanFriendlyEndDate, highlight) {
		$(".ui-tooltip").hide();
		++this.newReleaseCounter;
		var endDate = new Date(endDateTimestamp);
		var releaseIDStringLi = (releaseID > 0) ? ("or-release-" + releaseID) : ("or-newrelease-" + this.newReleaseCounter);
		var releaseIDStringTable = (releaseID > 0) ? ("or-table-release-" + releaseID) : ("or-table-newrelease-" + this.newReleaseCounter);
		var li = $("<li></li>").addClass("or-release active");
		li.attr("id", releaseIDStringLi);

		// header
		var dateLabel = $("<span></span>").addClass("badge or-date-badge waves-effect");
		if (humanFriendlyEndDate != null) {
			dateLabel.append($("<i></i>").addClass("material-icons").text("date_range"));
            dateLabel.append($("<span></span>").addClass("or-date-badge-text").text(humanFriendlyEndDate));
		}

		var editReleaseTitleRow = $("<div></div>").addClass("row or-release-title-row or-row-vertical-align");
		var editReleaseNameInput = $("<input />").addClass("form-control or-editable-form-field or-form-field or-form-release-title-field or-form-field-disabled")
                                                       .attr("type", "text")
                                                       .attr("placeholder", "Name of the release")
                                                       .attr("name", "or-release-title[]")
                                                       .attr("autocomplete", "off")
                                                       .val(releaseName)
                                                       .prop("disabled", true);
		var editReleaseCapacityInput = $("<input />").addClass("form-control or-form-release-capacity-field")
                                                       .attr("type", "number")
                                                       .attr("min", "0")
                                                       .attr("max", "10000")
                                                       .attr("step", "10")
                                                       .attr("name", "or-release-capacity[]")
                                                       .attr("autocomplete", "off")
                                                       .val(capacity);
		var editReleaseNameInputColumn = $("<div></div>").addClass("col-sm-6 or-form-edit-release-description-col").append(editReleaseNameInput);
		var editReleaseNameLink = $("<a></a>").attr("href", "#").addClass("or-form-edit-release-description").append($("<i></i>").addClass("material-icons dp48").text("edit"));
		var editReleaseDescriptionLinkColumn = $("<div></div>").addClass("col-sm-1 or-form-edit-release-description-link-col").append(editReleaseNameLink);
        var capacityColumn = $("<div></div>").addClass("col-sm-2 or-form-edit-release-capacity-col")
            .append($("<label></label>").text("Capacity"))
            .append(editReleaseCapacityInput);
		var endDateField = $("<input />").attr("type", "hidden")
                                               .attr("name", "or-release-end-date[]")
                                               .addClass("datepicker or-form-release-end-date-field or-form-release-date-field")
                                               .attr("data-initial-value", formatDate(endDate))
                                               .val(formatDate(endDate));
		var dateColumn = $("<div></div>").addClass("col-sm-2 or-form-edit-release-date-col")
                                         .append(dateLabel)
                                         .append(endDateField);
		var deleteButton = $("<a></a>").addClass("or-delete-release-button")
                                       .attr("href", "#")
                                       .append($("<i></i>").addClass("or-big-icon material-icons right").text("delete"));
		var deleteColumn = $("<div></div>").addClass("col-sm-1").append(deleteButton);
		editReleaseTitleRow
            .append(editReleaseNameInputColumn)
            .append(editReleaseDescriptionLinkColumn)
            .append(capacityColumn)
            .append(dateColumn)
            .append(deleteColumn);
		var content = $("<div></div>").addClass("collapsible-header or-ignore-click")
                                      .addClass("active")
                                      .append(editReleaseTitleRow);
		li.append(content);

		// body
		var body = $("<div></div>").addClass("collapsible-body").addClass("active").css("display", "none");
		var editReleaseDescriptionRow = $("<div></div>").addClass("row or-row-vertical-align");
		var editReleaseDescriptionInput = $("<textarea></textarea>").addClass("materialize-textarea form-control or-editable-form-textarea-field or-form-field or-form-release-description-field or-form-field-disabled")
                                                                    .attr("rows", "3")
                                                                    .attr("placeholder", "Description of the release")
                                                                    .attr("name", "or-release-description[]")
                                                                    .val(releaseDescription)
                                                                    .prop("disabled", true);
		var editReleaseDescriptionInputColumn = $("<div></div>").addClass("col-sm-11 or-form-edit-release-description-col").attr("style", "margin:0;padding:0;").append(editReleaseDescriptionInput);
		var editReleaseDescriptionLink = $("<a></a>").attr("href", "#").addClass("or-form-edit-release-description").append($("<i></i>").addClass("material-icons dp48").text("edit"));
		var editReleaseDescriptionLinkColumn = $("<div></div>").addClass("col-sm-1 or-form-edit-release-description-link-col").append(editReleaseDescriptionLink);
		editReleaseDescriptionRow.append(editReleaseDescriptionInputColumn).append(editReleaseDescriptionLinkColumn);
		body.append($("<p></p>").addClass("flow-text").append(editReleaseDescriptionRow));

		var requirementsTitle = $("<div></div>").attr("style", "font-weight:bold;").text("Requirements:");
		var requirementsTable = $("<table></table>").addClass("or-project-requirement-table")
                                                    .attr("id", releaseIDStringTable)
                                                    .attr("style", "width: 100%;");

		var theadRow = $("<tr></tr>");
		var thRequirementMoveCol = $("<th></th>").addClass("or-requirement-move").attr("style", "width:40px;");
		var thRequirementDeleteCol = $("<th></th>").addClass("or-requirement-delete").attr("style", "width:40px;");
		theadRow.append(thRequirementMoveCol)
                .append($("<th></th>").attr("style", "width:40px;").text("ID"))
                .append($("<th></th>").attr("style", "width:auto;").text("Title"))
                .append($("<th></th>").addClass("or-requirement-description or-mobile-invisible").text("Description"))
                .append($("<th></th>").addClass("or-requirement-status or-mobile-invisible").text("Status"))
                .append($("<th></th>").addClass("or-requirement-social-popularity"))
                //.append($("<th></th>").addClass("or-requirement-message"))
                .append($("<th></th>").addClass("or-requirement-basic-evaluation").text("Utility"))
                .append($("<th></th>").addClass("or-requirement-normal-evaluation").text("Utility"))
                .append($("<th></th>").addClass("or-requirement-advanced-evaluation").text("Utility"))
                .append($("<th></th>").addClass("or-requirement-assigned-stakeholder"))
                .append(thRequirementDeleteCol);
		if (this.uiEventHandler.isMoveable) { thRequirementMoveCol.show(); } else { thRequirementMoveCol.hide(); }
		if (this.uiEventHandler.isDeleteable) { thRequirementDeleteCol.show(); } else { thRequirementDeleteCol.hide(); }

		var requirementsTableHead = $("<thead></thead>").append(theadRow);
		requirementsTable.append(requirementsTableHead);
		requirementsTable.append($("<tbody></tbody>"));
		var addRequirementButtonContainer = $("<div></div>").addClass("or-add-button-container");
		var addRequirementButton = $("<a></a>").addClass("or-add-requirement-button waves-effect waves-light btn blue darken-3")
                                               .append("<i class=\"material-icons left\">add</i> Add Requirement");
		addRequirementButtonContainer.append(addRequirementButton);
		if (this.dataManager.projectData.projectSettings.readOnly) {
            addRequirementButtonContainer.hide();
        }

        var noRequirementsDiv = $("<div></div>")
            .addClass("or-release-empty or-release-empty-" + releaseID + " alert alert-warning")
            .append("<i class=\"material-icons\" style=\"color:#dad6c1;\">info</i>")
            .append($("<span></span>").text("No requirements have been assigned to this release so far."))
            .hide();
		body.append($("<div></div>")
            //.append(requirementsTitle)
            .append(noRequirementsDiv)
            .append(requirementsTable)
            .append(addRequirementButtonContainer));
		body.show();
		li.append(body);
		$("ul.or-release-list").append(li);
		$("#or-no-release-found-placeholder").hide();
		if (highlight) {
			$("body, html").animate({
				scrollTop: $("body").height()
			}, 800, (function() {
				li.effect("highlight", {}, 3000);
			})(li));
		}
		$(".or-add-requirement-button").unbind("click");
		$(".or-add-requirement-button").click(bindUIEvent(this.uiEventHandler, "addRequirementClickEvent"));
		if (releaseID == 0) {
			this.addRequirementFormFields(requirementsTable, null, this.dataManager.ratingAttributeData, false, false, true);
		}
		this.uiEventHandler.ignoreClickHandler();
		return requirementsTable;
	}

	addRequirementFormFields(tableSelector, requirementData, ratingAttributeData, highlight, autofocus, showRequirementID) {
        var currentUserID = this.dataManager.currentUserID;
        var requirementID = 0;
        var projectSpecificRequirementId = 0;
        var requirementTitle = null;
        var requirementDescription = null;
        var socialPopularity = 0.0;
        var anonymousRatings = [];
        var userBasicRatings = {};
        var yourBasicRating = 0;
        var userRatings = {};
        var yourRating = [];
        var userBasicRatings = {};
        var yourBasicRating = 0;
        var requirementMessages = {};
        var numberOfComments = 0;
        var numberOfPros = 0;
        var numberOfNeus = 0;
        var numberOfCons = 0;
        var responsibleStakeholders = [];
        var status = null;
        var importID = null;

        if (requirementData != null) {
            requirementID = requirementData.id;
            projectSpecificRequirementId = requirementData.projectSpecificRequirementId;
            requirementTitle = requirementData.title;
            requirementDescription = requirementData.description;
            socialPopularity = requirementData.socialPopularity;
            anonymousRatings = requirementData.anonymousRatings;
            userBasicRatings = requirementData.userBasicRatings;
            yourBasicRating = requirementData.yourBasicRating;
            userRatings = requirementData.userRatings;
            yourRating = requirementData.yourRating;
            userBasicRatings = requirementData.userBasicRatings;
            yourBasicRating = requirementData.yourBasicRating;
            requirementMessages = requirementData.messages;
            numberOfComments = requirementData.numberOfComments;
            numberOfPros = requirementData.numberOfPros;
            numberOfNeus = requirementData.numberOfNeus;
            numberOfCons = requirementData.numberOfCons;
            responsibleStakeholders = requirementData.responsibleStakeholders;
            status = requirementData.status;
            importID = requirementData.importID;
        }

        var isPrivateProject = this.dataManager.projectData.isPrivateProject;
        var tr = $("<tr></tr>");

		tr.addClass("or-requirement");
		if (requirementID > 0) {
			tr.attr("id", "or-requirement-" + requirementID);
			tr.attr("data-id", requirementID);
		}

		//var display =  ? "block" : "none";
 		var td = $("<td></td>");
 		td.addClass("or-requirement-move");
		td.append($("<i class=\"material-icons right\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Click and drag to move\" style=\"font-size:40px;\">drag_handle</i>"));
		if (this.uiEventHandler.isMoveable) {
			tr.addClass("or-moveable");
			td.show();
		} else {
			td.hide();
		}

		tr.append(td);

		if (showRequirementID) {
            $(".or-requirement-icon .or-requirement-icon-id-label:last").text((projectSpecificRequirementId > 0) ? projectSpecificRequirementId : "-");
            var requirementIcon = $(".or-requirement-icon:last");
            requirementIcon.attr("class", "or-requirement-icon or-requirement-icon-standard");
            var requirementIconContent = requirementIcon.wrap('<p/>').parent().html();
            requirementIcon.unwrap();
            var td = $("<td></td>");
            td.addClass("or-requirement-icon-container");
            td.attr("data-issue-counter", 0);
            var requirementSVGIcon = $(requirementIconContent);
            td.append(requirementSVGIcon);
            tr.append(td);
		}

		var titleField = $("<input type=\"text\" class=\"or-requirement-title form-control\" placeholder=\"Title\" name=\"or-requirement-title[]\" autocomplete=\"off\" />");
		if (importID != null) {
            titleField.attr("data-import-id", importID);
        }
		td = $("<td></td>");
		td.append(titleField);
		tr.append(td);

		var descriptionField = $("<div class=\"or-requirement-description\"></div>");

		td = $("<td></td>").addClass("or-requirement-description or-mobile-invisible");
		td.append(descriptionField);
		tr.append(td);

        td = $("<td></td>").addClass("or-requirement-status or-mobile-invisible");
        /* suitable icons: fiber_new, schedule, check, clear */
        var select = $("<select></select>").addClass("or-requirement-status-field");
        select.append($("<option></option>").val("NEW").text("New"));
        select.append($("<option></option>").val("INDISCUSSION").text("In discussion"));
        select.append($("<option></option>").val("PLANNED").text("Planned"));
        select.append($("<option></option>").val("ONGOING").text("Ongoing"));
        select.append($("<option></option>").val("COMPLETED").text("Completed"));
        select.append($("<option></option>").val("REJECTED").text("Rejected"));
        if (status != null) {
            select.val(status);
        }
        td.append(select);
        tr.append(td);

        td = $("<td></td>").addClass("or-requirement-social-popularity");
		var div = $("<div></div>").append($("<div></div>").append($("<i></i>").addClass("fa fa-twitter")))
                                  .append($("<div></div>").text(((socialPopularity != null) ? socialPopularity.toFixed(2) : "-")));

		if (socialPopularity != null) {
            div.attr("data-toggle", "tooltip")
               .attr("data-placement", "bottom")
               .attr("title", "This value is computed by analyzing the popularity on Twitter.");
		}

		td.append(div);
		tr.append(td);

        /*
        td = $("<td></td>");
        if ((requirementID > 0) && isPrivateProject) {
            td.addClass("or-requirement-message");
            var proConIndicator = $("<div></div>").addClass("or-procon-indicator");
            updateProConIndicator(proConIndicator, numberOfPros, numberOfNeus, numberOfCons);
            var div = $("<div></div>").append($("<div></div>")
                                          .append($("<i></i>").addClass("material-icons").text("message")))
                                      .append($("<div></div>")
                                          .addClass("or-comment-count")
                                          .attr("data-comment-count", numberOfComments)
                                          .text(numberOfComments + " comments"))
                                      .append(proConIndicator);
            td.append(div);
        }
        tr.append(td);
        */

        td = $("<td></td>");
        td.addClass("or-requirement-basic-evaluation");
        var showRatingStars = false;

        if (requirementID > 0) {
            //var ratings = (isPrivateProject ? userBasicRatings : anonymousRatings);
            var ratings = userBasicRatings;
            var ratingCount = Object.keys(ratings).length;
            var averageResult = computeAverageRating(ratings);
            var hasCurrentUserNotRated = (yourBasicRating == 0);
            var select = $("<select></select>")
                .addClass("or-requirement-basic-evaluation-field")
                .addClass("or-requirement-basic-evaluation-field-" + requirementID);
            select.append($("<option></option>").val("1").text("1"));
            select.append($("<option></option>").val("2").text("2"));
            select.append($("<option></option>").val("3").text("3"));
            select.append($("<option></option>").val("4").text("4"));
            select.append($("<option></option>").val("5").text("5"));
            select.attr("data-current-rating", averageResult.toFixed(1));
            td.append(select);

            if (hasCurrentUserNotRated) {
                td.append($("<div></div>").addClass("or-evaluation-result center").append($("<div></div>").addClass("or-rate-link").append("<i class=\"material-icons\">rate_review</i>").append("Click to rate")));
            } else {
                td.append($("<div></div>").addClass("or-evaluation-result center").text(averageResult.toFixed(2)));
                showRatingStars = true;
            }
            td.append($("<div></div>").addClass("or-average-rating-user-count").text(ratingCount + " user" + (ratingCount != 1 ? "s" : "") + " voted"));

            var inconsistencyLabel = $("<div></div>").addClass("or-average-rating-inconsistency");
            inconsistencyLabel.hide();
            td.append(inconsistencyLabel);

            if (!hasCurrentUserNotRated) {
                showBasicRatingConflict(inconsistencyLabel, ratings);
            }
        }

        tr.append(td);

        td = $("<td></td>").addClass("or-requirement-normal-evaluation");

        if (requirementID > 0) {
			var ratings = (isPrivateProject ? userRatings : anonymousRatings);
			var ratingCount = Object.keys(ratings).length;

			var mautResult = computeMAUT(ratingAttributeData, ratings,
				requirementData.ratingForwardDelegations, requirementData.ratingBackwardDelegations);
			var mautResultPercentage = Math.round(mautResult * 100);
			var hasCurrentUserNotRated = (yourRating == null) || Object.keys(yourRating).length == 0;
			var hasCurrentUserNotDelegatedVote = (requirementData.yourRatingDelegation == null) || Object.keys(requirementData.yourRatingDelegation).length == 0;

			//var rating = $("<div></div>").addClass("or-average-rating-bar");
			//rating.append($("<span style=\"width:" + mautResultPercentage + "%\" class=\"or-average-rating-bar-indicator\"></span>"))
			//td.append(rating);

			if (hasCurrentUserNotRated && hasCurrentUserNotDelegatedVote) {
				td.append($("<div></div>").addClass("center").append($("<div></div>").addClass("or-rate-link").append("<i class=\"material-icons\">rate_review</i>").append("Click to rate")));
			} else {
				td.append($("<div></div>").addClass("center").text(mautResult.toFixed(2)));
			}
			td.append($("<div></div>").addClass("or-average-rating-user-count").text(ratingCount + " user" + (ratingCount != 1 ? "s" : "") + " voted"));

			var inconsistencyLabel = $("<div></div>").addClass("or-average-rating-inconsistency");
			inconsistencyLabel.hide();
			td.append(inconsistencyLabel);

			if (!hasCurrentUserNotRated || !hasCurrentUserNotDelegatedVote) {
                showRatingConflict(inconsistencyLabel, ratingAttributeData, ratings);
            }
		}

		tr.append(td);

        td = $("<td></td>").addClass("or-requirement-advanced-evaluation");

        if (requirementID > 0) {
            var ratedUsers = {};
            for (var i in requirementMessages) {
                ratedUsers[requirementMessages[i].userData.userID] = true;
            }
            var ratingCount = Object.keys(ratedUsers).length;
            var ratings = convertMessagesToRatings(ratingAttributeData, requirementMessages);
            var mautResult = computeMAUT(ratingAttributeData, ratings, {}, {});
            var hasCurrentUserNotRated = !(currentUserID in ratedUsers);

            if (hasCurrentUserNotRated) {
                td.append($("<div></div>").addClass("center").append($("<div></div>").addClass("or-rate-link").append("<i class=\"material-icons\">rate_review</i>").append("Click to rate")));
            } else {
                td.append($("<div></div>").addClass("center").text(mautResult.toFixed(2)));
            }
            td.append($("<div></div>").addClass("or-average-rating-user-count").text(ratingCount + " user" + (ratingCount != 1 ? "s" : "") + " voted"));

            var inconsistencyLabel = $("<div></div>").addClass("or-average-rating-inconsistency");
            inconsistencyLabel.hide();
            td.append(inconsistencyLabel);

            showArgumentMAUTScore(inconsistencyLabel, requirementData, requirementData.messages, ratingAttributeData,
                this.dataManager, hasCurrentUserNotRated, false);
        }

        tr.append(td);

        td = $("<td></td>").addClass("or-requirement-assigned-stakeholder");

        if (requirementID > 0) {
            td.append($("<div></div>").addClass("center")
                .append($("<div></div>").addClass("or-stakeholder-assign-link")
                    .append("<i class=\"material-icons\">person_add</i>"))
                .append($("<div></div>").addClass("or-assigned-stakeholder-count")
                    .attr("data-stakeholder-count", responsibleStakeholders.length)
                    .text(responsibleStakeholders.length + " user" + ((responsibleStakeholders.length != 1) ? "s" : ""))));
        }

        tr.append(td);

        td = $("<td></td>").addClass("or-requirement-delete");
		td.append($("<a class=\"or-delete-button btn-floating btn-small waves-effect waves-light red lighten-2\"><i class=\"material-icons\">delete</i></a>"));
        if (this.uiEventHandler.isDeleteable) { td.show(); } else { td.hide(); }
		tr.append(td);
		tableSelector.children("tbody").append(tr);
		if (this.uiEventHandler.isMoveable) {
			this.uiEventHandler.bindUIEvents();
			tr.draggable("enable");
		}
		if (autofocus) { titleField.focus(); }
		if (highlight) { tr.effect('highlight', {}, 3000); }
		$("#or-save-button-container").show();
		$(".or-delete-button").unbind("click");
		$(".or-delete-button").click(bindUIEvent(this.uiEventHandler, "deleteRequirementEvent"));
		$('[data-toggle="tooltip"]').tooltip();
        $("select.or-requirement-status-field").material_select();
        $("span.caret").text("");

        descriptionField.summernote({
            minHeight: 40,
            maxHeight: 80,
            airMode: true,
            placeholder: 'Description',
            callbacks: {
                onFocus: function() {
                    $(this)
                        .parent()
                        .children(".note-editor")
                        .children(".note-editing-area")
                        .children(".note-editable")
                        .addClass("or-description-active");
                },
                onBlur: function() {
                    $(this)
                        .parent()
                        .children(".note-editor")
                        .children(".note-editing-area")
                        .children(".note-editable")
                        .removeClass("or-description-active");
                }
            }
        });

        if (requirementID > 0) {
            titleField.val((requirementTitle != null) ? requirementTitle : "");
            if (requirementDescription != null) {
                descriptionField.summernote("code", requirementDescription);
            }
        }

        if (showRatingStars) {
            $(".or-requirement-basic-evaluation-field-" + requirementID).barrating('show', {
                theme: 'fontawesome-stars-o',
                initialRating: averageResult.toFixed(1),
                allowEmpty: false,
                showSelectedRating: true,
                showValues: false,
                deselectable: false
            }).barrating("readonly", true);
        }
        this.showVisiblePanelsAndViews();
        return tr;
    }

	save(event, thisObj) {
		this.uiEventHandler.disableMovingRequirementsEvent(event, thisObj);
		this.uiEventHandler.disableDeletingRequirementsEvent(event, thisObj);
		var uiManager = this;

		var loadingAnimation = new LoadingAnimation();
		var reference = this;
		loadingAnimation.run(event, function () {
			var dataManager = reference.dataManager;
			reference.saveHandler.save(reference.dataManager.collectChanges(true), function (success) {
				if (success) {
				    uiManager.hideUnsavedChangesNotificationBar();
					dataManager.fetchAndShowRequirementsAndReleases(function () { $("#or-animation-overlay").fadeOut(); });
				}
			}.bind(dataManager));
		}.bind(reference));
		return false;
	}

    checkInconsistencies(event, thisObj) {
		this.dataManager.fetchIssues(function () {
            this.showIssues();
        }.bind(this));
	}
}

class LoadingAnimation {

	constructor() {
		this.ripple_wrap = $('.ripple-wrap');
		this.rippler = $('.ripple');
		this.finish = false;
	    var reference = this;
		this.rippler.bind("webkitAnimationEnd oAnimationEnd msAnimationEnd mozAnimationEnd animationend", function (e) {
			reference.ripple_wrap.removeClass('goripple');
		}.bind(reference));
		this.callback = null;
	}

	run(e, callback) {
		this.rippler.css("left", e.clientX + "px");
		this.rippler.css("top", e.clientY + "px");
		this.finish = false;
		this.ripple_wrap.addClass("goripple");
		this.callback = callback;
		var reference = this;
		window.requestAnimationFrame(function() {
			reference.monitor(reference.rippler[0])
		}.bind(reference));
	}

	monitor(el) {
		var computed = window.getComputedStyle(el, null);
		var borderwidth = parseFloat(computed.getPropertyValue('border-left-width'));
		if (!this.finish && borderwidth >= 1500) {
			el.style.WebkitAnimationPlayState = "paused";
			el.style.animationPlayState = "paused";
			this.swapContent();
		}
		if (this.finish) {
			el.style.WebkitAnimationPlayState = "running";
			el.style.animationPlayState = "running";
			return;
		} else {
			var reference = this;
			window.requestAnimationFrame(function() {
				reference.monitor(el);
			}.bind(reference));
		}
	}

	swapContent() {
	    // do some Ajax, put it in the DOM and then set this to true
	    if (!$("#or-animation-overlay").is(':visible')) {
	    		$("#or-animation-overlay").show();
	    		this.callback();
	    } else {
	    		$("#or-animation-overlay").hide();
	    }
	    var reference = this;
	    setTimeout(function() {
	        reference.finish = true;
	    }.bind(reference), 10);
	}
}

class SaveHandler {

	constructor(projectID) {
		this.projectID = projectID;
		this.callQueue = [];
		this.changes = null;
		this.completeCallback = null;
	}

	save(changes, completeCallback) {
		this.callQueue = [];
		this.changes = changes;
		this.completeCallback = completeCallback;

		for (var j in this.changes.newRequirements) {
			var newRequirement = this.changes.newRequirements[j];
			if (newRequirement.assignedRelease == null) {
				newRequirement.assignedReleaseID = 0;
				continue;
			}

			if (newRequirement.assignedRelease.split("-")[2] != "newrelease") {
				var releaseID = parseInt(newRequirement.assignedRelease.split("-")[3]);
				newRequirement.assignedReleaseID = releaseID;
			}
		}

		for (var j in this.changes.updatedRequirements) {
			var updatedRequirement = this.changes.updatedRequirements[j];
			if (updatedRequirement.assignedRelease == null) {
				updatedRequirement.assignedReleaseID = 0;
				continue;
			}

			if (updatedRequirement.assignedRelease.split("-")[2] != "newrelease") {
				var releaseID = parseInt(updatedRequirement.assignedRelease.split("-")[3]);
				updatedRequirement.assignedReleaseID = releaseID;
			}
		}

		if (("projectName" in changes.updatedProjectData) || ("projectDescription" in changes.updatedProjectData)) {
			this.callQueue.push(bindSaveHandlerNextEvent(this, "updateProject"));
            $(document).attr("title", "OpenReq!Live - " + changes.updatedProjectData.projectName);
		}

		if (this.changes.deletedRequirements.length > 0) {
			this.callQueue.push(bindSaveHandlerNextEvent(this, "deleteRequirements"));
		}

		if (this.changes.deletedReleases.length > 0) {
			this.callQueue.push(bindSaveHandlerNextEvent(this, "deleteReleases"));
		}

		if (this.changes.newReleases.length > 0) {
			this.callQueue.push(bindSaveHandlerNextEvent(this, "createReleases"));
		}

		if (this.changes.newRequirements.length > 0) {
			this.callQueue.push(bindSaveHandlerNextEvent(this, "createRequirements"));
		}

		if (this.changes.updatedReleases.length > 0) {
			this.callQueue.push(bindSaveHandlerNextEvent(this, "updateReleases"));
		}

		if (this.changes.updatedRequirements.length > 0) {
			this.callQueue.push(bindSaveHandlerNextEvent(this, "updateRequirements"));
		}

		this.handleNextCall();
	}

	handleNextCall() {
		if (this.callQueue.length == 0) {
			this.completeCallback(true);
			return;
		}

		var nextCall = this.callQueue[0];
		this.callQueue.shift();
		nextCall();
	}

	updateProject() {
		console.log("Update project...");
		var url = "/project/" + this.projectID + "/update.json";
		$.ajax(url, {
			'data': this.changes.updatedProjectData,
			'type': 'POST',
			'success': bindAJAXSuccessHandler(this, "successCallback"),
			'error': bindAJAXErrorHandler(this, "errorCallback")
		});
	}

	deleteRequirements() {
		console.log("Delete requirements...");
		var url = "/project/" + this.projectID + "/requirement/delete.json";
		var requirementIDs = this.changes.deletedRequirements.map(function(requirementData) { return requirementData.id; });
		$.ajax(url, {
			'data': { "requirementID[]": requirementIDs },
			'type': 'POST',
			'success': bindAJAXSuccessHandler(this, "successCallback"),
			'error': bindAJAXErrorHandler(this, "errorCallback")
		});
	}

	deleteReleases() {
		console.log("Delete releases...");
		var url = "/project/" + this.projectID + "/release/delete.json";
		var releaseIDs = this.changes.deletedReleases.map(function(releaseData) { return releaseData.id; });
		$.ajax(url, {
			'data': { "releaseID[]": releaseIDs },
			'type': 'POST',
			'success': bindAJAXSuccessHandler(this, "successCallback"),
			'error': bindAJAXErrorHandler(this, "errorCallback")
		});
	}

	createReleases() {
		console.log("Create releases...");
		var url = "/project/" + this.projectID + "/release/create.json";
		var jsonifiedString = JSON.stringify(this.changes.newReleases);
		$.ajax(url, {
		    'data': jsonifiedString,
			'type': 'POST',
		    'contentType': 'application/json',
		    'processData': false,
			'success': bindAJAXExtendedSuccessHandler(this, "successCallback", function (thisObj, result) {
				var newReleaseCounter = 1;
				for (var i in result.releaseIDs) {
					while (thisObj.changes.ignoredNewReleaseNumbers.indexOf(newReleaseCounter) > -1) {
						++newReleaseCounter;
					}

					var newReleaseID = result.releaseIDs[i];
					for (var j in thisObj.changes.newRequirements) {
						var newRequirement = thisObj.changes.newRequirements[j];
						if (newRequirement.assignedRelease == null) {
							newRequirement.assignedReleaseID = 0;
							continue;
						}
						var releaseIDOrCounter = parseInt(newRequirement.assignedRelease.split("-")[3]);
						if ((newRequirement.assignedRelease.split("-")[2] == "newrelease") && (releaseIDOrCounter == newReleaseCounter)) {
							newRequirement.assignedReleaseID = newReleaseID;
						}
					}
					for (var j in thisObj.changes.updatedRequirements) {
						var updatedRequirement = thisObj.changes.updatedRequirements[j];
						if (updatedRequirement.assignedRelease == null) {
							updatedRequirement.assignedReleaseID = 0;
							continue;
						}
						var releaseIDOrCounter = parseInt(updatedRequirement.assignedRelease.split("-")[3]);
						if ((updatedRequirement.assignedRelease.split("-")[2] == "newrelease") && (releaseIDOrCounter == newReleaseCounter)) {
							updatedRequirement.assignedReleaseID = newReleaseID;
						}
					}
					++newReleaseCounter;
				}
			}),
			'error': bindAJAXErrorHandler(this, "errorCallback")
		});
	}

	createRequirements() {
		console.log("Create requirements...");
		var url = "/project/" + this.projectID + "/requirement/create.json";
		var jsonifiedString = JSON.stringify(this.changes.newRequirements);
		$.ajax(url, {
		    'data': jsonifiedString,
			'type': 'POST',
		    'contentType': 'application/json',
		    'processData': false,
			'success': bindAJAXSuccessHandler(this, "successCallback"),
			'error': bindAJAXErrorHandler(this, "errorCallback")
		});
	}

	updateReleases() {
		console.log("Update releases...");
		var url = "/project/" + this.projectID + "/release/update.json";
		var jsonifiedString = JSON.stringify(this.changes.updatedReleases);
		$.ajax(url, {
		    'data': jsonifiedString,
			'type': 'POST',
		    'contentType': 'application/json',
		    'processData': false,
			'success': bindAJAXSuccessHandler(this, "successCallback"),
			'error': bindAJAXErrorHandler(this, "errorCallback")
		});
	}

	updateRequirements() {
		console.log("Update requirements...");
		var url = "/project/" + this.projectID + "/requirement/update.json";
		var jsonifiedString = JSON.stringify(this.changes.updatedRequirements);
		$.ajax(url, {
		    'data': jsonifiedString,
			'type': 'POST',
		    'contentType': 'application/json',
		    'processData': false,
			'success': bindAJAXSuccessHandler(this, "successCallback"),
			'error': bindAJAXErrorHandler(this, "errorCallback")
		});
	}

	successCallback(reference, result, status, xhr) {
		if (result.error) {
			alert("An error occured: " + result.errorMessage);
			this.completeCallback(false);
			return false;
		}

		// optional parameter callback
		if (arguments.length == 5) {
	        var callback = arguments[4];
	        callback(this, result);
	    }

		this.handleNextCall();
	}

	errorCallback(reference, xhr, status, error) {
		console.log(error);
		this.completeCallback(false);
	}

}

class UIEventHandler {

	constructor() {
		this.uiManager = null;
		this.dataManager = null;
		this.isMoveable = false;
		this.isDeleteable = false;
		this.searchSuggestions = {};
		this.datePickerOptions = null;
		this.isInvitationModeActive = false;
	}

	prepare() {
        $("ul.or-release-list > li").slice(1).each(function (index, liSelector) { this.expandReleaseContainer($(liSelector)); });
        this.ignoreClickHandler();
		this.bindUIEvents();
	}

	bindUIEvents() {
		var selector = $("#or-project-name, .or-project-description, .or-form-release-title-field, .or-form-release-description-field, .or-requirement-title, div.or-requirement-description");
		selector.unbind("keyup").on("keyup", bindUIEvent(this, "unsavedChangesEvent"));
        $(".or-requirement-status-field").unbind("change");
        $("#show-releases-filter").unbind("change");
        $(".or-delete-button").unbind("click");
        $(".or-delete-issue-button").unbind("click");
		$("#or-add-release-button").unbind("click");
        $("#or-add-unassigned-requirement-button").unbind("click");
		$(".or-add-requirement-button").unbind("click");
		$("#or-save-button").unbind("click");
		$("#or-check-button").unbind("click");
		$(".or-delete-release-button").unbind("click");
        $(".or-date-badge").unbind("click");
        $("#or-assessment-scheme-button").unbind("click");
        $("#or-import-requirements-twitter-button").unbind("click");
		$("#or-move-requirements-button").unbind("click");
		$("#or-delete-requirements-button").unbind("click");
		$("#or-project-settings-button").unbind("click");
        $(".or-requirement-basic-evaluation").unbind("click");
		$(".or-requirement-normal-evaluation").unbind("click");
		$(".or-requirement-advanced-evaluation").unbind("click");
        $(".or-delete-rating-attribute-button").unbind("click");
        $(".or-rating-self-vote").unbind("click");
        $(".or-rating-delegate-vote").unbind("click");
        $(".or-delegate-vote-user-btn").unbind("click");
        $(".or-remove-delegation-btn").unbind("click");
        $(".or-share-button").unbind("click");
		$(".or-invite-user-btn").unbind("click");
		$(".or-uninvite-user-btn").unbind("click");
		$(".or-uninvite-guest-user-btn").unbind("click");	
		$("input.or-search-user:last").unbind("keyup");
        $(".or-stakeholder-assign-link").unbind("click");
        $(".or-requirement-quality-link").unbind("click");
        $(".or-assign-stakeholder-btn").unbind("click");
        $(".or-unassign-user-btn").unbind("click");
        $(".or-requirement-message").unbind("click");
        $(".or-message-assignment").unbind("click");
        $(".or-message-submit").unbind("click");
        $(".or-delete-comment-btn").unbind("click");
        $(".or-rate-stakeholder-btn").unbind("click");
        $(".or-accept-stakeholder-assignment-btn").unbind("click");
        $(".or-unaccept-stakeholder-assignment-btn").unbind("click");
        $(".or-container").unbind("click");
        $(".or-add-dependency-button-link").unbind("click");
        $(".or-delete-dependency-button").unbind("click");
        $(".or-dependency-type-menu-item").unbind("click");
        $(".or-import-dependency-button-link").unbind("click");
        $(".or-select-menu-item").unbind("click");
        $(".or-form-edit-release-description").unbind("click");
        $("#or-notification-button-container").unbind("click");
        $("#or-add-issue-button").unbind("click");
        $(".or-add-rating-attribute-button").unbind("click");
        $(".or-tweets-category-container").unbind("click");
        $(".or-import-requirement-button").unbind("click");

        $(".or-delete-button").on("click", bindUIEvent(this, "deleteRequirementEvent"));
        $(".or-delete-issue-button").on("click", bindUIEvent(this, "deleteIssueEvent"));
        $("#or-add-release-button").click(bindUIEvent(this, "addReleaseClickEvent"));
        $("#or-add-unassigned-requirement-button").click(bindUIEvent(this, "addUnassignedRequirementClickEvent"));
		$(".or-add-requirement-button").click(bindUIEvent(this, "addRequirementClickEvent"));
        $(".or-requirement-status-field").on("change", bindUIEvent(this, "unsavedChangesEvent"));
        $("#show-releases-filter").on("change", bindUIEvent(this, "filterReleasesEvent"));
        $("#or-save-button").on("click", bindUIEvent(this.uiManager, "save"));
		$("#or-check-button").on("click", bindUIEvent(this.uiManager, "checkInconsistencies"));
		$(".or-delete-release-button").click(bindUIEvent(this, "deleteReleaseEvent"));

		if (!this.dataManager.projectData.projectSettings.readOnly) {
            $(".or-form-edit-release-description").bind("click", bindUIEvent(this, "editReleaseDescriptionEvent"));
        } else {
		    $(".or-form-edit-release-description").bind("click", function () {
                alert("You are not allowed to change the title or description of a release! This is a read-only project.");
                return false;
            });
        }
		$(".or-form-field").on("focusout", bindUIEvent(this, "focusOutEditReleaseDescriptionEvent"));
        if (!this.dataManager.projectData.projectSettings.readOnly) {
            $(".or-date-badge").bind("click", bindUIEvent(this, "datePickerEvent"));
        } else {
            $(".or-date-badge").bind("click", function (e) {
                alert("You are not allowed to change the deadline of a release! This is a read-only project.");
                return false;
            });
        }
		$(".or-visibility-button").on("click", bindUIEvent(this, "updateVisibilityEvent"));
        $("#or-assessment-scheme-button").on("click", bindUIEvent(this, "specifyAssessmentSchemeEvent"));
        $("#or-import-requirements-twitter-button").bind("click", bindUIEvent(this, "importRequirementsFromTwitterClickEvent"));
		$("#or-move-requirements-button").on("click", bindUIEvent(this, "moveableRequirementsEvent"));
		$("#or-delete-requirements-button").on("click", bindUIEvent(this, "deleteableRequirementsEvent"));
        $("#or-project-settings-button").on("click", bindUIEvent(this, "projectSettingsEvent"));
        $(".or-requirement-basic-evaluation").on("click", bindUIEvent(this, "basicRequirementEvaluationEvent"));
        $(".or-requirement-normal-evaluation").on("click", bindUIEvent(this, "rateEvent"));
        $(".or-requirement-advanced-evaluation").on("click", bindUIEvent(this, "messageClickEvent"));
        $(".or-delete-rating-attribute-button").on("click", bindUIEvent(this, "deleteRatingAttributeClickEvent"));
        $(".or-rating-self-vote").on("click", bindUIEvent(this, "rateEvent"));
        $(".or-rating-delegate-vote").on("click", bindUIEvent(this, "rateDelegateEvent"));
        $(".or-delegate-vote-user-btn").on("click", bindUIEvent(this, "rateDelegateClickEvent"));
        $(".or-remove-delegation-btn").on("click", bindUIEvent(this, "removeDelegationClickEvent"));
        $(".or-share-button").on("click", bindUIEvent(this, "shareEvent"));
		$(".or-invite-user-btn").on("click", bindUIEvent(this, "inviteUserClickEvent"));
		$(".or-uninvite-user-btn").on("click", bindUIEvent(this, "uninviteUserClickEvent"));
		$(".or-uninvite-guest-user-btn").on("click", bindUIEvent(this, "uninviteGuestUserClickEvent"));
		$("input.or-search-user:last").on("keyup", bindUIEvent(this, "searchUserEvent"));
        $(".or-stakeholder-assign-link").on("click", bindUIEvent(this, "assignStakeholderEvent"));
        $(".or-requirement-quality-link").on("click", bindUIEvent(this, "requirementQualityClickEvent"));
        $(".or-assign-stakeholder-btn").on("click", bindUIEvent(this, "assignStakeholderClickEvent"));
        $(".or-unassign-user-btn").on("click", bindUIEvent(this, "unassignUserClickEvent"));
        $(".or-requirement-message").on("click", bindUIEvent(this, "messageClickEvent"));
        $(".or-message-assignment").on("click", bindUIEvent(this, "messageAssignmentClickEvent"));
        $(".or-message-submit").on("click", bindUIEvent(this, "messageSendClickEvent"));
        $(".or-delete-comment-btn").on("click", bindUIEvent(this, "deleteCommentClickEvent"));
        $(".or-rate-stakeholder-btn").on("click", bindUIEvent(this, "rateStakeholderClickEvent"));
        $(".or-accept-stakeholder-assignment-btn").on("click", bindUIEvent(this, "acceptStakeholderClickEvent"));
        $(".or-unaccept-stakeholder-assignment-btn").on("click", bindUIEvent(this, "unacceptStakeholderClickEvent"));
        $(".or-container").on("click", bindUIEvent(this, "navigationClickEvent"));
        $(".or-add-dependency-button-link").bind("click", bindUIEvent(this, "createDependencyClickEvent"));
        $(".or-delete-dependency-button").bind("click", bindUIEvent(this, "deleteDependencyClickEvent"));
        $(".or-dependency-type-menu-item").bind("click", bindUIEvent(this, "changeDependencyTypeClickEvent"));
        $(".or-import-dependency-button-link").bind("click", bindUIEvent(this, "importRecommendedDependenciesClickEvent"));
        $(".or-select-menu-item").bind("click", bindUIEvent(this, "selectMenuItemEvent"));
        $("#or-notification-button-container").bind("click", bindUIEvent(this, "openNotificationCenterEvent"));
        $("#or-add-issue-button").bind("click", bindUIEvent(this, "addIssueEvent"));
        $(".or-add-rating-attribute-button").bind("click", bindUIEvent(this, "addRatingAttributeEvent"));
        $(".or-tweets-category-container").bind("click", bindUIEvent(this, "showTweetsContainerEvent"));
        $(".or-import-requirement-button").bind("click", bindUIEvent(this, "importTwitterRequirementEvent"));

        if (this.dataManager.projectData.projectSettings.readOnly) {
            $(".or-form-edit-release-description").css("color", "#d2d2d2").css("cursor", "default");
            $(".or-date-badge").css("opacity", "0.5");
            $(".or-delete-release-button").hide();
        }

        $("textarea").each(function () { autosize(this); });

        $(".or-sentiment-pro-area").unbind("click");
        $(".or-sentiment-con-area").unbind("click");
        $(".or-sentiment-neutral-area").unbind("click");

        $(".or-sentiment-pro-area").click(function () { $(this).children(".or-sentiment-pro").prop("checked", true); });
        $(".or-sentiment-con-area").click(function () { $(this).children(".or-sentiment-con").prop("checked", true); });
        $(".or-sentiment-neutral-area").click(function () { $(this).children(".or-sentiment-neutral").prop("checked", true); });
        $('[data-toggle="tooltip"]').tooltip();

        /*
        $(".or-release > .collapsible-header").each(function(counter) {
            var $this = $(this);
            var $target = $(this).parent(".or-release"); //.children(".collapsible-body");
            var additionalOffsetFactor = 1;
            if (counter > 0) {
                additionalOffsetFactor = 2;
            }
            var headerHeight = 73;//$(".collapsible-header").height();
            console.log(headerHeight);
            $this.pushpin({
                top: $target.offset().top + 50 - (headerHeight * additionalOffsetFactor),
                bottom: $target.offset().top + $target.outerHeight() - $this.height(),
                //offset:  ( + 50) // + headerHeight
            });
        });
        */

        var c = {};
		var uiEventHandler = this;
		$(".or-moveable").draggable({
	        helper: function() {
                return $(this).clone();//$("<div></div>").append($(this).clone());
            },
	        scroll: true,
			revert: function (isValidDrop) {
                if (!isValidDrop) {
                    $(this).show();
	            } else {
                    $(this).remove();
	            }
                $(".or-delete-release-button").show();
                $(".or-form-edit-release-description-link-col").show();
                $("ul.or-release-list > li").not("#or-no-release-found-placeholder").each(function (index, liSelector) {
                    $(liSelector).removeClass("ui-droppable");
                    uiEventHandler.expandReleaseContainer($(liSelector));
                }.bind(uiEventHandler));
                return !isValidDrop;
	        },
			cursor: "move",
			//cursorAt: { top: 56, left: 56 },
	        start: function (event, ui) {
                $(".ui-tooltip").hide();
                $(this).hide();
                var requirementContainer = $(this).parent("tbody").parent("table").parent("div");
                var requirementTable = $(this).parent("tbody").parent("table");
                var currentLiSelector = requirementContainer.hasClass("collapsible-header") ? requirementContainer.parent("li") : requirementContainer.parent("div").parent("li");
                var context = { uiEventHandler: uiEventHandler, currentLiSelector: currentLiSelector, currentRequirementTable: requirementTable };
                $("ul.or-release-list > li").not("#or-no-release-found-placeholder").add("#or-project-unassigned-requirements-container").each(function (index, liSelector) {
                		if ($(liSelector).attr("id") == context.currentLiSelector.attr("id")) {
                			return true;
                		}

            			context.uiEventHandler.collapseReleaseContainer($(liSelector));
        				$(liSelector).attr("data-droppable", "true");
                		$(liSelector).droppable({
                		      classes: {
                		        "ui-droppable-active": "or-state-active",
                		        "ui-droppable-hover": "or-state-hover"
                		      },
                		      drop: function (event, ui) {
                		    	  	if (context.currentRequirementTable.children("tbody").children("tr").length == 2) {
                		    	  		uiEventHandler.uiManager.addRequirementFormFields(
            		    	  				context.currentRequirementTable, null,
                                            uiEventHandler.dataManager.ratingAttributeData, false, false, true
                		    	  		);
                		    	  	}

                		    	  	var targetTbody = null;
                		    	  	if ($(liSelector).attr("id") == "or-project-unassigned-requirements-container") {
                		    	  		targetTbody = $("#or-project-unassigned-requirements > tbody");
                		    	  	} else {
                                        var releaseID = parseInt($(liSelector).attr("id").split("-")[2]);
                                        $(".or-release-empty-" + releaseID).hide();
                                        $("#or-table-release-" + releaseID).show();
                                        targetTbody = $(this).children(".collapsible-body").children("div").children(".or-project-requirement-table").children("tbody");
                		    	  	}
                		    	  	var draggedRequirementTableRow = $(ui.helper).clone();
                		    	  	if (draggedRequirementTableRow.attr("data-id") !== undefined) {
                		    	  		draggedRequirementTableRow.attr("id", "or-requirement-" + draggedRequirementTableRow.attr("data-id"));
                		    	  	}
                		    	  	draggedRequirementTableRow.css({ 'position' : '', 'left' : '', 'top' : '' });
                		    	  	targetTbody.prepend(draggedRequirementTableRow);
                		    	  	targetTbody.effect("highlight", {}, 3000);
                                    $("div.or-requirement-description").summernote({
                                        minHeight: 40,
                                        maxHeight: 80,
                                        airMode: true,
                                        placeholder: 'Description',
                                        callbacks: {
                                            onFocus: function() {
                                                $(this)
                                                    .parent()
                                                    .children(".note-editor")
                                                    .children(".note-editing-area")
                                                    .children(".note-editable")
                                                    .addClass("or-description-active");
                                            },
                                            onBlur: function() {
                                                $(this)
                                                    .parent()
                                                    .children(".note-editor")
                                                    .children(".note-editing-area")
                                                    .children(".note-editable")
                                                    .removeClass("or-description-active");
                                            }
                                        }
                                    });
                                    context.uiEventHandler.bindUIEvents();

                		    	  	// TODO: fix this...
                		    	  	//       HACK/WORKAROUND: timeout necessary otherwise the dragged requirement will be
                		    	  	//       accidentally detected as new requirement and update requirement
                		    	  	window.setTimeout(function () { context.uiEventHandler.unsavedChangesEvent(); }, 100);
                		      }
                	    }).droppable("enable");
                }.bind(context));

                $("ul.or-release-list > li").not("#or-no-release-found-placeholder").add("#or-project-unassigned-requirements-container").each(function (index, liSelector) {
	            		if ($(liSelector).attr("id") != context.currentLiSelector.attr("id")) {
	            			return true;
	            		}

	            		var isDroppable = $(liSelector).attr("data-droppable");
            			if (isDroppable == "true") {
            				$(liSelector).droppable("disable").removeClass("ui-droppable-active").remove("or-state-active");
            				$(liSelector).removeAttr("data-droppable");
            			}
	            	}.bind(context));

                $(".or-delete-release-button").hide();
                $(".or-form-edit-release-description-link-col").hide();
	            c.tr = this;
	            c.helper = ui.helper;
	        }
		});
		/*
		$("ul.or-release-list > li").not("#or-no-release-found-placeholder").droppable({
		      classes: {
		        "ui-droppable-active": "or-state-active",
		        "ui-droppable-hover": "or-state-hover"
		      },
		      drop: function (event, ui) {
		    	  	alert("Dropped!");
		        //$(this).addClass("ui-state-highlight").find("p").html("Dropped!");
		      }
	    });*/
	}

	createUserTableRow(user, subtitle, isCreator, isCurrentUser, removeClass, showConfirmationText, removeData) {
		var isCurrentUserLabel = isCurrentUser ? " <span style=\"color:#AAAAAA;\">(you)</span>" : "";
		var divName = $("<div></div>").html((user.firstName + " " + user.lastName) + isCurrentUserLabel);
		var divStatus = $("<div></div>").addClass("or-shared-users-table-email");

		if (subtitle != null) {
		    divStatus.text(subtitle);
        } else {
		    divStatus.text((isCreator || (!showConfirmationText)) ? "" : (user.isAccepted ? "Confirmation accepted" : "Confirmation pending..."));
        }

		var aRemove = $("<a></a>").attr("href", "#")
            .addClass(removeClass)
            .html("<i class=\"material-icons\">close</i></a>");

		if (removeData !== undefined) {
		    for (var key in removeData) {
		        aRemove.attr(key, removeData[key]);
            }
        }

		var tr = $("<tr></tr>");
		tr.attr("id", "or-participating-user-" + user.id);
        var userImage = "<img src=\"" + ((user.profileImagePath != null) ? user.profileImagePath : "/images/userimage.png") + "\" class=\"or-participating-user-avatar or-avatar\" />";
		tr.append($("<td></td>").append(userImage));
		tr.append($("<td></td>").append(divName).append(divStatus));
		var tdRemoveCell = $("<td></td>").addClass("or-shared-users-table-remove-cell");
		if (!isCreator) {
			tdRemoveCell.append(aRemove);
		}
		tr.append(tdRemoveCell);
		return tr;
	}

	createGuestUserTableRow(guestUserParticipationData, showUninviteButton) {
		var divEmail = $("<div></div>").html(guestUserParticipationData.email);
		var divStatus = $("<div></div>").addClass("or-shared-users-table-email").text("Confirmation pending...");
		var aRemove = $("<a></a>").attr("href", "#").addClass("or-uninvite-guest-user-btn").html("<i class=\"material-icons\">close</i></a>");

		var tr = $("<tr></tr>");
		tr.attr("data-email", guestUserParticipationData.email);
		tr.append($("<td></td>").html("<img src=\"/images/userimage.png\" width=\"40\" />"));
		tr.append($("<td></td>").append(divEmail).append(divStatus));
		var tdRemoveCell = $("<td></td>").addClass("or-shared-users-table-remove-cell");
		if (showUninviteButton) {
			tdRemoveCell.append(aRemove);
		}
		tr.append(tdRemoveCell);
		return tr;
	}

	shareEvent(event, thisObj) {
        $(".dropdown-button").dropdown("close");
        thisObj.blur();
        $(".ui-tooltip").hide();
        var projectID = this.uiManager.projectID;
		var isPrivateProject = this.uiManager.dataManager.projectData.isPrivateProject;
		var uiManager = this.uiManager;
		var currentUserID = uiManager.dataManager.currentUserID;
		var dataManager = this.uiManager.dataManager;
		$(".or-shared-users-table > tbody > tr:not(.or-invite-user-loading-animation)").remove();
		$(".or-invite-user-loading-animation").hide();

		$.getJSON("/project/" + projectID + "/user/list.json", function(data) {
			for (var i in data.participatingUsers) {
				var tr = this.createUserTableRow(data.participatingUsers[i], null, false, (data.participatingUsers[i].id == currentUserID), "or-uninvite-user-btn", true);
				$(".or-shared-users-table > tbody").prepend(tr);
			}

			for (var i in data.invitedGuestUsers) {
				var tr = this.createGuestUserTableRow(data.invitedGuestUsers[i], true);
				$(".or-shared-users-table > tbody").prepend(tr);
			}

			if (data.creatorUser != null) {
				var tr = this.createUserTableRow(data.creatorUser, null, true, (data.creatorUser.id == currentUserID), "or-uninvite-user-btn", true);
				$(".or-shared-users-table > tbody").prepend(tr);
			}

			var sharedUsersContainer = $(".or-shared-users-container");
			var sharedUsersContainerContent = sharedUsersContainer.wrap('<p/>').parent().html();
			sharedUsersContainer.unwrap();
			this.showShareDialogEvent(sharedUsersContainerContent);
		}.bind(this));
		return false;
	}

	showShareDialogEvent(sharedUsersContainerContent) {
        this.isInvitationModeActive = true;
		swal({
			html: '<div class="or-share-area">'
				+ sharedUsersContainerContent
				+ '</div>',
				showCancelButton: false,
				confirmButtonText: "Close"
		}).then(function (result) {
            this.isInvitationModeActive = false;
            return true;
        }.bind(this));
		this.bindUIEvents();
	}

	searchUserEvent(event, thisObj) {
	    var projectKey = this.uiManager.projectKey;
        var isPrivateProject = this.uiManager.dataManager.projectData.isPrivateProject;
        if (isEnterKey(event.which)) {
            $(thisObj).parent("div").parent(".or-input-submit-row").children().children(".or-complete-action-btn").click();
            event.preventDefault();
            return true;
        }
        if (isArrowKey(event.which) || !isPrivateProject) {
		    event.preventDefault();
		    return true;
		}

		var uiEventHandler = this;
	    var searchTerm = $(thisObj).val();
        var type = this.isInvitationModeActive ? "projectInvitation" : "assignStakeholder";

		$.ajax("/user/search.json", {
			"data": {
			    query: searchTerm,
                projectKey: projectKey,
                type: type
            },
			"type": "POST",
			"success": function (response) {
                uiEventHandler.searchSuggestions = {};
                uiEventHandler.searchSuggestions.emailAddresses = response.users.map(u => u.email);
                uiEventHandler.searchSuggestions.suggestions = {};
                for (var i in response.users) {
                    var user = response.users[i];
                    var suggestionTitle = user.firstName + " " + user.lastName + " (" + user.email + ")";
                    uiEventHandler.searchSuggestions.suggestions[suggestionTitle] = (user.profileImagePath != null) ? user.profileImagePath : "/images/userimage.png";
                }

                for (var i in TOP_LEVEL_DOMAINS) {
                        var domain = TOP_LEVEL_DOMAINS[i];
                        var emailParts = searchTerm.split(".");
                        if (emailParts.length == 1) {
                            continue;
                        }
                        var lastEmailPart = emailParts[emailParts.length - 1];
                        if ((lastEmailPart == "") || (domain.indexOf(lastEmailPart) == -1)) {
                            continue;
                        }
                        var autocompletedEmailAddress = emailParts.slice(0, (emailParts.length - 1)).join(".") + "." + domain;
                    if ((uiEventHandler.searchSuggestions.emailAddresses.indexOf(autocompletedEmailAddress) == -1) && isValidEmailAddress(autocompletedEmailAddress)) {
                        uiEventHandler.searchSuggestions.suggestions[autocompletedEmailAddress] = "/images/email.png";
                    }
                }

                $(thisObj).autocomplete({
                    data: uiEventHandler.searchSuggestions.suggestions,
                    limit: 5,
                    onAutocomplete: function(val) {},
                    minLength: 1,
                });
			}
		});
	}

    createRequirementAssignedUserTableRow(requirementID, userID, isAccepted, isHidden, proposedBy, isAnonymousUser, stakeholderRatingAttributeData, stakeholderVotes, fullName, profileImagePath, isCurrentUser, currentUserID, creatorUserID) {
        var isCurrentUserLabel = isCurrentUser ? " <span style=\"color:#AAAAAA;\">(you)</span>" : "";
        var rateStakeholderButton = $("<a></a>")
            .attr("href", "#")
            .addClass("or-rating-specific-content or-rate-stakeholder-btn")
            .attr("data-requirement-id", requirementID)
            .attr("data-user-id", userID)
            .attr("data-is-anonymous-user", isAnonymousUser)
            .attr("data-user-fullname", fullName)
            .attr("title", "Rate " + fullName)
            .append("<i class=\"material-icons right\">rate_review</i>");

        var hideStakeholderButton = $("<a></a>")
            .attr("href", "#")
            .addClass("or-rating-specific-content or-rate-stakeholder-btn")
            .attr("data-requirement-id", requirementID)
            .attr("data-user-id", userID)
            .attr("data-is-anonymous-user", isAnonymousUser)
            .attr("data-user-fullname", fullName)
            .attr("title", "Hide " + fullName)
            .append("<i class=\"material-icons right\">visibility_off</i>");

        var acceptStakeholderAssignmentButton = $("<a></a>")
            .attr("href", "#")
            .addClass("or-rating-specific-content")
            .addClass(isAccepted ? "or-unaccept-stakeholder-assignment-btn" : "or-accept-stakeholder-assignment-btn")
            .attr("data-requirement-id", requirementID)
            .attr("data-user-id", userID)
            .attr("data-is-anonymous-user", isAnonymousUser)
            .append("<i class=\"material-icons right\">person_pin</i>");

        if (isAccepted) {
            acceptStakeholderAssignmentButton.attr("title", isCurrentUser ? "Unaccept me" : ("Unaccept " + fullName));
        } else {
            acceptStakeholderAssignmentButton.attr("title", isCurrentUser ? "Accept me" : ("Accept " + fullName));
        }

        var divName = $("<div></div>").html(fullName + isCurrentUserLabel);
        if (isCurrentUser || (creatorUserID == currentUserID)) {
            divName.append(acceptStakeholderAssignmentButton);
        }

        divName.append(rateStakeholderButton);
        /*
        divName.append($("<div></div>")
            .attr("data-toggle", "tooltip")
            .attr("data-placement", "bottom")
            .attr("title", "Proposed by Requirements Manager")
            .addClass("or-suggested-label")
            .addClass("or-suggested-label-rm")
            .addClass("or-help")
            .text("RM"));
        */
        if (proposedBy == 0) {
            divName.append($("<div></div>")
                .attr("data-toggle", "tooltip")
                .attr("data-placement", "bottom")
                .attr("title", "Proposed by Artificial Intelligence")
                .addClass("or-suggested-label")
                .addClass("or-suggested-label-bot")
                .addClass("or-help")
                .text("AI"));
        } else {
            divName.append($("<div></div>")
                .attr("data-toggle", "tooltip")
                .attr("data-placement", "bottom")
                .attr("title", "Proposed by Stakeholder")
                .addClass("or-suggested-label")
                .addClass("or-suggested-label-others")
                .addClass("or-help")
                .text("ST"));
        }
        var divStatus = $("<div></div>").addClass("or-accepted-stakeholder-label").text("");
        var aRemove = $("<a></a>")
            .attr("href", "#")
            .addClass("or-unassign-user-btn" + (isAnonymousUser ? " or-anonymous-user" : ""))
            .attr("data-user-id", userID)
            .attr("title", isCurrentUser ? "Unassign me" : ("Unassign " + fullName))
            .html("<i class=\"material-icons\">close</i></a>");

        var tr = $("<tr></tr>");
        var userImage = "<img src=\"" + ((profileImagePath != null) ? profileImagePath : "/images/userimage.png")
            + "\" class=\"or-participating-user-avatar or-avatar\" />";
        tr.addClass("or-assigned-stakeholder");
        tr.addClass("or-assigned-" + (isAnonymousUser ? "anonymous" : "") + "user-" + userID);
        if (isAccepted) {
            tr.addClass("or-accepted-stakeholder");
            divStatus.text("Accepted");
        }
        tr.append($("<td></td>").append(userImage));
        tr.append($("<td></td>").append(divName).append(divStatus));

        var isLoggedIn = (currentUserID > 0);

        // Appropriateness
        var mautResult = null;
        var ratingAttribute = stakeholderRatingAttributeData.filter(ratingAttribute => ratingAttribute.name == "Appropriateness")[0];
        var sumAttributeVote = 0;
        var numberAttributeVotes = 0;
        var yourAttributeVote = null;
        if (isLoggedIn) {
            for (var userID in stakeholderVotes) {
                var voteData = stakeholderVotes[userID];
                for (var j in voteData.attributeVotes) {
                    var attributeVote = voteData.attributeVotes[j];
                    if (ratingAttribute.id != attributeVote.attributeID) {
                        continue;
                    }

                    sumAttributeVote += attributeVote.value;
                    ++numberAttributeVotes;
                    if (userID == currentUserID) {
                        yourAttributeVote = attributeVote.value;
                    }
                }
            }
        }

        tr.append($("<td></td>").addClass("or-rating-specific-content or-left-rating-border center").text((yourAttributeVote != null) ? yourAttributeVote : "-"));

        if (numberAttributeVotes > 0) {
            var averageVoting = sumAttributeVote / parseFloat(numberAttributeVotes);
            tr.append($("<td></td>").addClass("or-rating-specific-content center").text(averageVoting.toFixed(1)));
            mautResult = averageVoting * ratingAttribute.weight;
        } else {
            tr.append($("<td></td>").addClass("or-rating-specific-content center").text("-"));
        }

        // Availability
        ratingAttribute = stakeholderRatingAttributeData.filter(ratingAttribute => ratingAttribute.name == "Availability")[0];
        sumAttributeVote = 0;
        numberAttributeVotes = 0;
        yourAttributeVote = null;
        if (isLoggedIn) {
            for (var userID in stakeholderVotes) {
                var voteData = stakeholderVotes[userID];
                for (var j in voteData.attributeVotes) {
                    var attributeVote = voteData.attributeVotes[j];
                    if (ratingAttribute.id != attributeVote.attributeID) {
                        continue;
                    }

                    sumAttributeVote += attributeVote.value;
                    ++numberAttributeVotes;
                    if (userID == currentUserID) {
                        yourAttributeVote = attributeVote.value;
                    }
                }
            }
        }

        tr.append($("<td></td>").addClass("or-rating-specific-content or-left-rating-border center").text((yourAttributeVote != null) ? yourAttributeVote : "-"));

        if (numberAttributeVotes > 0) {
            var averageVoting = sumAttributeVote / parseFloat(numberAttributeVotes);
            tr.append($("<td></td>").addClass("or-rating-specific-content center").text(averageVoting.toFixed(1)));
            mautResult = (mautResult != null) ? mautResult : 0.0;
            mautResult += averageVoting * ratingAttribute.weight;
        } else {
            tr.append($("<td></td>").addClass("or-rating-specific-content center").text("-"));
        }

        tr.append($("<td></td>").addClass("or-rating-specific-content or-left-rating-border or-right-rating-border center").css("font-weight", "bold").text((mautResult != null) ? mautResult.toFixed(1) : "-"));

        var tdRemoveCell = $("<td></td>").addClass("or-assigned-users-table-remove-cell");
        if (proposedBy != 0) {
            tdRemoveCell.append(aRemove);
        }
        tr.append(tdRemoveCell);
        return tr;
    }

    addIssueRow(title, description, messageCounter, priority, deleteIssueID) {
        var tr = $("<tr></tr>");
        var div = $("<div></div>");
        var priorityRow = $("<td></td>")
            .addClass("or-priority")
            .text(Object.keys(NotificationPriority).filter(function(key) { return NotificationPriority[key] == priority })[0]);
        if (priority == NotificationPriority.CRITICAL) {
            tr.addClass("or-issue-failed");
            priorityRow.addClass("or-priority-critical");
        }
        div.append($("<h3></h3>").text(title));
        div.append($("<p></p>").text(description));
        tr.append($("<td></td>").text(messageCounter));
        tr.append($("<td></td>").addClass("or-issue-description").append(div));
        tr.append(priorityRow);
        var td = $("<td></td>");
        if (deleteIssueID != null) {
            var deleteLink = $("<a></a>").addClass("or-delete-issue-button btn-floating btn-small waves-effect waves-light red lighten-2");
            deleteLink.attr("data-issue-id", deleteIssueID);
            deleteLink.append($("<i></i>").addClass("material-icons").text("delete"));
            td.append(deleteLink);
        }
        tr.append(td);
        $("#or-project-issues > tbody").append(tr);
    }

    assignStakeholderClickEvent(event, thisObj) {
        var searchField = $(".or-search-user:last");
        var requirementID = $(".or-assigned-stakeholders-container").attr("data-requirement-ID");
        if (searchField.val() == "") {
            swal("Error", "Please enter a valid name of a user!", "error").then(function (result) {
                this.assignStakeholderEvent(event, thisObj, requirementID);
            }.bind(this));
            return false;
        }
        var projectID = this.uiManager.projectID;
        var uiEventHandler = this;
        var uiManager = this.uiManager;
        var dataManager = uiManager.dataManager;
        var isPrivateProject = dataManager.projectData.isPrivateProject;
        var creatorUserID = dataManager.projectData.creatorUserID;
        var currentUserID = dataManager.currentUserID;
        $(".or-assign-stakeholder-loading-animation").show();
        $(".or-search-user").prop('disabled', true);
        $(".or-assign-stakeholder-btn").attr('disabled', true);

        $.ajax("/project/" + projectID + "/requirement/" + requirementID + "/user/assign.json", {
            'data': { query: searchField.val() },
            'type': 'POST',
            'success': function (result) {
                if (result.error == true) {
                    $(".or-search-user").prop('disabled', false);
                    $(".or-assign-stakeholder-btn").attr('disabled', false);
                    swal("Error", result.errorMessage, "error").then(function (result) {
                        uiEventHandler.assignStakeholderEvent(event, thisObj, requirementID);
                    });
                    return false;
                }

                var tr = null;
                if ("userData" in result) {
                    var fullName = result.userData.firstName + " " + result.userData.lastName;
                    tr = uiEventHandler.createRequirementAssignedUserTableRow(requirementID, result.userData.id, false,
                        false, currentUserID, false, dataManager.stakeholderRatingAttributeData, [],
                        fullName, result.userData.profileImagePath, (result.userData.id == currentUserID), currentUserID, creatorUserID);
                } else {
                    tr = uiEventHandler.createRequirementAssignedUserTableRow(requirementID, result.anonymousUserData.id,
                        false, false, -1, true, dataManager.stakeholderRatingAttributeData,
                        [], result.anonymousUserData.fullName, null, false, currentUserID, creatorUserID);
                }
                tr.hide();
                $(".or-assign-stakeholder-loading-animation").hide();
                $(".or-assign-stakeholder-placeholder").hide();
                $(".or-assigned-stakeholders-table > tbody").append(tr);
                tr.fadeIn("slow");
                if (!isPrivateProject) {
                    $(".or-rating-specific-content").hide();
                }
                $(".or-search-user").prop('disabled', false);
                $(".or-assign-stakeholder-btn").attr('disabled', false);
                searchField.val("").focus();

                var assignedStakeholderCountDiv = $("#or-requirement-" + requirementID + " .or-assigned-stakeholder-count");
                var numberOfAssignedStakeholders = parseInt(assignedStakeholderCountDiv.attr("data-stakeholder-count")) + 1;
                assignedStakeholderCountDiv.attr("data-stakeholder-count", numberOfAssignedStakeholders);
                assignedStakeholderCountDiv.text(numberOfAssignedStakeholders + " user" + ((numberOfAssignedStakeholders != 1) ? "s" : ""));

                uiEventHandler.initializeBarRating();
                uiEventHandler.bindUIEvents();
            }
        });
        return false;
    }

    initializeBarRating() {
        $(".or-rating-field").barrating('show', {
            theme: 'bars-1to10',
            allowEmpty: true,
            showSelectedRating: true,
            showValues: false,
            deselectable: true,
            onSelect: function(value, text, event) {
                /*
                if (typeof(event) !== 'undefined') {
                  // rating was selected        by a user
                  console.log(event.target);
                } else {
                  // rating was selected programmatically
                  // by calling `set` method
                }
            */
            }
        }).barrating('clear');
    }

	inviteUserClickEvent(event, thisObj) {
		var searchField = $(".or-search-user:last");
		if (searchField.val() == "") {
			swal("Error", "Please enter a valid name of a user or a valid email address!", "error").then(function (result) {
				this.shareEvent(event, thisObj);
            }.bind(this));
			return false;			
		}
		var projectID = this.uiManager.projectID;
		var uiEventHandler = this;
		var uiManager = this.uiManager;
		var currentUserID = uiManager.dataManager.currentUserID;
		$(".or-invite-user-loading-animation").show();
		$(".or-search-user").prop('disabled', true);
		$(".or-invite-user-btn").attr('disabled', true);

		$.ajax("/project/" + projectID + "/user/invite.json", {
			'data': { query: searchField.val() },
			'type': 'POST',
			'success': function (result) {
				if (result.error == true) {
					$(".or-search-user").prop('disabled', false);
					$(".or-invite-user-btn").attr('disabled', false);
					swal("Error", result.errorMessage, "error").then(function (result) {
						uiEventHandler.shareEvent(event, thisObj);
		            });
					return false;
				}

				var tr = null;
				if ("userParticipationData" in result) {
					tr = uiEventHandler.createUserTableRow(result.userParticipationData, null, false, (result.userParticipationData.id == currentUserID), "or-uninvite-user-btn", true);
				} else {
					tr = uiEventHandler.createGuestUserTableRow(result.guestUserParticipationData, true);
				}
				tr.hide();
				$(".or-invite-user-loading-animation").hide();
				$(".or-shared-users-table > tbody").append(tr);
				tr.fadeIn("slow");
				$(".or-search-user").prop('disabled', false);
				$(".or-invite-user-btn").attr('disabled', false);
				searchField.val("").focus();
				uiEventHandler.bindUIEvents();
			}
		});
		return false;
	}

	uninviteUserClickEvent(event, thisObj) {
		var projectID = this.uiManager.projectID;
		var uiEventHandler = this;
		var uiManager = this.uiManager;
		var currentUserID = uiManager.dataManager.currentUserID;
		var tr = $(thisObj).parent("td").parent("tr");
		var userID = parseInt(tr.attr("id").split("-")[3]);
		$.ajax("/project/" + projectID + "/user/" + userID + "/uninvite.json", {
			'type': 'GET',
			'success': function (result) {
				if (result.error == true) {
					swal("Error", result.errorMessage, "error");
					return false;
				}

				tr.fadeOut("slow", "swing", function () { tr.remove(); });

				if (userID == currentUserID) {
                    window.location = "/";
                    return;
				}
			}
		});
		return false;
	}

	uninviteGuestUserClickEvent(event, thisObj) {
		var projectID = this.uiManager.projectID;
		var uiEventHandler = this;
		var uiManager = this.uiManager;
		var currentUserID = uiManager.dataManager.currentUserID;
		var tr = $(thisObj).parent("td").parent("tr");
		var email = tr.attr("data-email");
		$.ajax("/project/" + projectID + "/user/guest/uninvite.json", {
			'data': { email: email },
			'type': 'POST',
			'success': function (result) {
				if (result.error == true) {
					swal("Error", result.errorMessage, "error");
					return false;
				}
				tr.fadeOut("slow", "swing", function () { tr.remove(); });
			}
		});
		return false;
	}

    unassignUserClickEvent(event, thisObj) {
        var projectID = this.uiManager.projectID;
        var requirementID = parseInt($(".or-assigned-stakeholders-container").attr("data-requirement-id"));
        var uiManager = this.uiManager;
        var currentUserID = uiManager.dataManager.currentUserID;
        var tr = $(thisObj).parent("td").parent("tr");
        var userID = parseInt($(thisObj).attr("data-user-id"));
        var isAnonymousUser = $(thisObj).hasClass("or-anonymous-user");
        $.ajax("/project/" + projectID + "/requirement/" + requirementID + "/user/" + userID + "/unassign.json", {
            'data': { isAnonymousUser: isAnonymousUser },
            'type': 'POST',
            'success': function (result) {
                if (result.error == true) {
                    swal("Error", result.errorMessage, "error");
                    return false;
                }

                tr.fadeOut("slow", "swing", function () {
                    if (tr.parent().children().length == 3) {
                        $(".or-assign-stakeholder-placeholder").show();
                    }
                    tr.remove();
                });

                var assignedStakeholderCountDiv = $("#or-requirement-" + requirementID + " .or-assigned-stakeholder-count");
                var numberOfAssignedStakeholders = parseInt(assignedStakeholderCountDiv.attr("data-stakeholder-count")) - 1;
                assignedStakeholderCountDiv.attr("data-stakeholder-count", numberOfAssignedStakeholders);
                assignedStakeholderCountDiv.text(numberOfAssignedStakeholders + " user" + ((numberOfAssignedStakeholders != 1) ? "s" : ""));
            }
        });
        return false;
    }

    selectMenuItemEvent(event, thisObj) {
        var checkBox = $(thisObj).children("span").children(".or-checkbox");
        if (checkBox.is(':checked')) {
            checkBox.prop("checked", false);
            checkBox.removeAttr("checked");
        } else {
            checkBox.prop("checked", true);
            checkBox.attr("checked", "checked");
        }

        if ($(thisObj).parent().hasClass("or-filter-dimension-menu") || $(thisObj).parent().hasClass("or-filter-conflict-menu")) {
            this.renderCommentListEvent(event, thisObj);
        }

        this.bindUIEvents();
    }

    openNotificationCenterEvent(event, thisObj) {
        var projectID = this.uiManager.projectID;
        var dataManager = this.uiManager.dataManager;
        if ($("#container-notification-center").is(":visible")) {
            $("#container-notification-center").hide();
            var activeContainers = 0;
            $(".or-container").each(function() {
                if ($(this).hasClass("active")) {
                    var containerID = $(this).attr("href");
                    $(containerID).show();
                    ++activeContainers;
                }
            });
            console.log(activeContainers);
            if (activeContainers > 1) {
                $("#or-tab-navigation-bar").show();
            }
            return false;
        }
        $(".or-container").each(function() {
            if ($(this).hasClass("active")) {
                var containerID = $(this).attr("href");
                $(containerID).hide();
            }
        });
        $("#container-notification-center").show();
        $("#or-tab-navigation-bar").hide();
        this.uiManager.notificationCenter.render();
        this.bindUIEvents();
        return false;
    }

    addIssueEvent(event, thisObj) {
        var uiEventHandler = this;
        var addIssueContainer = $(".or-add-issue-container");
        var addIssueContainerContent = addIssueContainer.wrap('<p/>').parent().html();
        addIssueContainer.unwrap();

        swal({
            html: '<div class="or-issue-container-area">' + addIssueContainerContent + '</div>',
            customClass: "or-modal-wide",
            showConfirmButton: true,
            showCancelButton: true,
            confirmButtonText: "Save",
            cancelButtonText: "Cancel"
        }).then(function (result) {
            if (("value" in result) && result.value) {
                uiEventHandler.saveIssueEvent(event, thisObj);
            }
            return true;
        });

        $("select.or-issue-priority-field").material_select();
        $("span.caret").text("");
        $(".swal2-popup").css("width", "600px");
        $(".or-issue-title-field").focus();
        return false;
    }

    addRatingAttributeEvent(event, thisObj) {
        var uiEventHandler = this;
        var addRatingAttributeContainer = $(".or-add-rating-attribute-container");
        var addRatingAttributeContainerContent = addRatingAttributeContainer.wrap('<p/>').parent().html();
        addRatingAttributeContainer.unwrap();

        swal({
            html: '<div class="or-rating-attribute-container-area">' + addRatingAttributeContainerContent + '</div>',
            customClass: "or-modal-wide",
            showConfirmButton: true,
            showCancelButton: true,
            confirmButtonText: "Save",
            cancelButtonText: "Cancel"
        }).then(function (result) {
            if (("value" in result) && result.value) {
                return uiEventHandler.saveRatingAttributeEvent(event, thisObj);
            }
            return true;
        });

        for (var i in ICON_NAMES) {
            var option = $("<option></option>").attr("value", ICON_NAMES[i])
                .html("<span class=\"material-icons\">" + ICON_NAMES[i] + "</span> " + ICON_NAMES[i]);
            $("select.or-rating-attribute-icon-field").append(option);
        }

        $("select.or-rating-attribute-icon-field").last().material_select();
        $("span.caret").text("");
        $(".or-rating-attribute-icon-field > input.select-dropdown").last().hide();
        $(".or-rating-attribute-icon-field > .caret").last().hide();
        $(".or-rating-attribute-icon-trigger-field").last().unbind("click");
        $(".or-rating-attribute-icon-trigger-field").last().on("click", function () {
            $(".or-rating-attribute-icon-field > input.select-dropdown").last().click();
            $(".or-rating-attribute-icon-field > ul.dropdown-content > li").on("click", function() {
                $(".or-rating-attribute-select-box").last().html($(this).html());
                $(".or-rating-attribute-select-box").last().attr("data-icon", $(this).children("span").children("span.material-icons").text());
                $(".or-rating-attribute-icon-field > input.select-dropdown").last().click();
                //$(".or-rating-attribute-icon-field > ul.dropdown-content").hide();
            });
        });
        $(".swal2-popup").css("width", "600px");
        $(".or-issue-title-field").focus();
	    return false;
    }

    showTweetsContainerEvent(event, thisObj) {
        $(".or-tweets-navigation-container > .tabs > .tab > .or-tweets-category-container").each(function () {
            var containerClass = $(this).attr("href").replace("#", ".");
            console.log(containerClass);
            $(this).removeClass("active");
            $(containerClass).hide();
        });
        var containerClass = $(thisObj).attr("href").replace("#", ".");
        $(thisObj).addClass("active");
        $(containerClass).fadeIn();
        this.bindUIEvents();
	    return false;
    }

    importTwitterRequirementEvent(event, thisObj) {
	    var tweetID = $(thisObj).attr("data-tweet-id");
	    $(thisObj).attr("disabled", "disabled");
	    var requirementText = $(thisObj).parent("td").parent("tr").children("td.or-tweet-message").text();
	    var tr = this.uiManager.addRequirementFormFields($("#or-project-unassigned-requirements"),
            null, [], false, false, true);
	    tr.children("td").children(".or-requirement-title").attr("data-import-id", tweetID).val("[" + tweetID + "] Imported from Twitter");
	    tr.children("td.or-requirement-description").children(".or-requirement-description").summernote("code", requirementText);
        this.bindUIEvents();
        this.uiManager.showUnsavedChangesNotificationIfNecessary();
	    return false;
    }

    saveRatingAttributeEvent(event, thisObj) {
        var projectID = this.uiManager.projectID;
        var name = $(".or-rating-attribute-title-field").last().val();
        var description = $(".or-rating-attribute-description-field").last().val();
        var iconName = $(".or-rating-attribute-select-box").last().attr("data-icon");
        var ratingAttributeData = {
            name: name,
            description: description,
            iconName: iconName,
            minValue: 1,
            maxValue: 5,
            interval: 1,
            weight: 1.0
        };

        if (name.length == 0) {
            alert("Please enter a title!");
            this.addRatingAttributeEvent(event, thisObj);
            return false;
        }

        if (description.length == 0) {
            alert("Please enter a description!");
            this.addRatingAttributeEvent(event, thisObj);
            return false;
        }

        var jsonifiedString = JSON.stringify(ratingAttributeData);
        var uiEventHandler = this;
        var dataManager = this.dataManager;

        $.ajax("/project/" + projectID + "/rating/attribute/create.json", {
            'data': jsonifiedString,
            'type': 'POST',
            'contentType': 'application/json',
            'processData': false,
            'success': function (result) {
                if (result.error == true) {
                    swal("Error", result.errorMessage, "error");
                    return false;
                }

                ratingAttributeData.id = result.attributeID;
                dataManager.ratingAttributeData.push(ratingAttributeData);
                uiEventHandler.specifyAssessmentSchemeEvent(event, thisObj);
                uiEventHandler.bindUIEvents();
            }
        });
	    return true;
    }

    saveIssueEvent(event, thisObj) {
        var projectID = this.uiManager.projectID;
        var title = $(".or-issue-title-field").last().val();
        var description = $(".or-issue-description-field").last().val();
        var priority = $("div.or-issue-priority-field").last().children("input.select-dropdown").val().toUpperCase();
        var jsonifiedString = JSON.stringify({
            title: title,
            description: description,
            priority: priority
        });

        var uiEventHandler = this;
        var dataManager = this.dataManager;
        $.ajax("/project/" + projectID + "/issue/create.json", {
            'data': jsonifiedString,
            'type': 'POST',
            'contentType': 'application/json',
            'processData': false,
            'success': function (result) {
                if (result.error == true) {
                    swal("Error", result.errorMessage, "error");
                    return false;
                }

                dataManager.issueData["issues"].push({
                    id: result.issueID,
                    title: title,
                    description: description,
                    status: status,
                    priority: priority
                });

                uiEventHandler.uiManager.showIssues();
                uiEventHandler.bindUIEvents();
            }
        });
	    return false;
    }

    deleteIssueEvent(event, thisObj) {
        var projectID = this.uiManager.projectID;
        var issueID = parseInt($(thisObj).attr("data-issue-id"));
        var tr = $(thisObj).parent("td").parent("tr");
        var uiEventHandler = this;
        var dataManager = this.dataManager;
        $.ajax("/project/" + projectID + "/issue/" + issueID + "/delete.json", {
            'type': 'GET',
            'contentType': 'application/json',
            'processData': false,
            'success': function (result) {
                if (result.error == true) {
                    swal("Error", result.errorMessage, "error");
                    return false;
                }

                dataManager.issueData["issues"] = dataManager.issueData["issues"].filter(function (issue) { return issue.id != issueID; });
                tr.remove();
                uiEventHandler.uiManager.showIssues();
                uiEventHandler.bindUIEvents();
            }
        });
        return false;
    }

    filterReleasesEvent(event, thisObj) {
        var uiManager = this.uiManager;
        var dataManager = uiManager.dataManager;
        var filterTextValue = $(thisObj).parent().children("input.select-dropdown").val();
        var valueMap = {
            "All releases": 0,
            "Only future releases": 1,
            "Only recent releases": 2,
            "Only past releases": 3
        };
        dataManager.filterReleases(valueMap[filterTextValue]);

        var loadingAnimation = new LoadingAnimation();
        var reference = this;
        loadingAnimation.run(event, function () {
            reference.dataManager.showReleasesRequirementsAndIssues();
            $("#or-animation-overlay").fadeOut();
        }.bind(reference));
	    return false;
    }

    renderCommentListEvent(event, thisObj) {
        var uiManager = this.uiManager;
        var dataManager = uiManager.dataManager;
        var currentUserID = dataManager.currentUserID;
        var requirementID = parseInt($(".or-message-area-container").attr("data-requirement-id"));
        var requirement = this.uiManager.dataManager.requirementData.filter(function (req) { return req.id == requirementID; })[0];
        var conflictMessageIDs = messagesWithConflicts(requirement);

        var ratingAttributeIDMap = {};
        for (var i in dataManager.ratingAttributeData) {
            var ratingAttributeInfo = dataManager.ratingAttributeData[i];
            ratingAttributeIDMap[ratingAttributeInfo.id] = ratingAttributeInfo;
        }

        var activatedDimensions = [];
        $(".or-filter-dimension-menu:last > .or-select-menu-item > span > .or-checkbox:checked").each(function () {
            activatedDimensions.push(parseInt($(this).attr("data-dimension")));
        });

        $(".or-message-container").children().remove();
        var requirementMessages = dataManager.requirementMessages[requirementID];
        var shownMessageCounter = 0;
        var showOnlyConflicts = $(".or-filter-conflict-menu:last .or-checkbox").is(":checked");

        for (var i in requirementMessages) {
            var assignedDimensions = requirementMessages[i].assignedDimensions;
            var sharedDimensions = assignedDimensions.filter(value => -1 !== activatedDimensions.indexOf(value));
            if (sharedDimensions.length == 0) {
                continue;
            }

            var userID = requirementMessages[i].userData.userID;
            var isCurrentUser = (currentUserID == userID);
            var id = requirementMessages[i].id;
            var title = requirementMessages[i].title;
            var profileImagePath = requirementMessages[i].userData.profileImagePath;
            var shortFullName = requirementMessages[i].userData.shortFullName;
            var sentiment = requirementMessages[i].sentiment;
            var createdDate = new Date(requirementMessages[i].createdDate);
            var conflictingCommentIDs = conflictMessageIDs[id];
            if (showOnlyConflicts && ((conflictingCommentIDs === undefined) || (conflictingCommentIDs.length == 0))) {
                continue;
            }
            var newMessageDiv = this.createCommentMessageDiv(requirementID, id, title, profileImagePath, sentiment,
                assignedDimensions, createdDate, shortFullName, isCurrentUser, conflictingCommentIDs, ratingAttributeIDMap);
            $(".or-message-container").prepend(newMessageDiv);
            ++shownMessageCounter;
        }

        if (shownMessageCounter == 0) {
            $(".or-no-message-found-warning").show();
        } else {
            $(".or-no-message-found-warning").hide();
        }

        return false;
    }

    messageClickEvent(event, thisObj, requirementID) {
        thisObj.blur();
        var uiEventHandler = this;
        var projectID = this.uiManager.projectID;
        var uiManager = this.uiManager;
        var dataManager = uiManager.dataManager;
        var currentUserID = dataManager.currentUserID;
        requirementID = (requirementID === undefined) ? parseInt($(thisObj).parent().attr("id").split("-")[2]) : requirementID;
        var requirement = this.uiManager.dataManager.requirementData.filter(function (req) { return req.id == requirementID; })[0];
        $(".or-message-area-container").attr("data-requirement-ID", requirementID);
        $(".or-message-container").children(".or-message").remove();
        $(".or-new-message").show();
        $(".or-new-message-assignment").hide();
        $(".or-argument-list-row").show();
        $(".or-sentiment").each(function () {
            $(this).prop("checked", false);
        });
        $(".or-select-dimension-menu").children().remove();
        $(".or-filter-dimension-menu").children().remove();
        $(".or-basic-rating-subtitle").text(requirement.title);

        for (var i in dataManager.ratingAttributeData) {
            var ratingAttribute = dataManager.ratingAttributeData[i];
            var attributeID = ratingAttribute.id;
            var attributeTitle = ratingAttribute.name;
            var selectItem = $("<li></li>").addClass("or-select-menu-item");
            var span = $("<span></span>")
                .attr("data-toggle", "tooltip")
                .attr("data-placement", "top")
                .attr("title", ratingAttribute.description);
            var checkBoxID = "select-dimension-" + attributeID;
            var input = $("<input />").addClass("or-checkbox filled-in")
                .attr("type", "checkbox")
                .attr("id", checkBoxID)
                .attr("data-dimension", attributeID);
            var label = $("<label />").addClass("or-checkbox-label")
                .attr("for", checkBoxID)
                .text(attributeTitle);
            var icon = $("<i></i>").addClass("or-rating-attribute-icon material-icons")
                .attr("for", checkBoxID)
                .text(ratingAttribute.iconName);

            var input2 = input.clone();
            var label2 = label.clone();
            checkBoxID = "filter-dimension-" + attributeID;
            input2.attr("id", checkBoxID);
            input2.attr("checked", "checked");
            input2.prop("checked", true);
            label2.attr("for", checkBoxID);

            span.append(input);
            span.append(label);
            span.append(icon);
            selectItem.append(span);

            var selectItem2 = $("<li></li>").addClass("or-select-menu-item");
            var span2 = $("<span></span>");
            span2.append(input2);
            span2.append(label2);
            span2.append(icon);
            span2.append($("<span></span>").addClass("argument-issues"));
            selectItem2.append(span2);

            $(".or-select-dimension-menu").append(selectItem);
            $(".or-filter-dimension-menu").append(selectItem2);
        }
        $("or-filter-conflict-menu:last .or-checkbox").prop("checked", false).removeAttr("checked");

        $.getJSON("/project/" + projectID + "/requirement/" + requirementID + "/message/list.json", function(data) {
            dataManager.requirementMessages[requirementID] = data.messages;
            requirement.messages = data.messages;
            uiEventHandler.renderCommentListEvent(event, thisObj);
            var messageContainer = $(".or-message-area-container");
            var messageContainerContent = messageContainer.wrap('<p/>').parent().html();
            messageContainer.unwrap();
            swal({
                html: '<div class="or-message-area">'
                + messageContainerContent
                + '</div>',
                showCancelButton: false,
                confirmButtonText: "Close"
            });
            $(".swal2-popup").css("width", "700px");
            $(".or-new-message-textarea").focus();
            var hasCurrentUserAlreadyRated = (data.messages.filter(m => m.userData.userID == currentUserID).length > 0);
            var inconsistencyLabel = $("#or-requirement-" + requirementID + " > .or-requirement-advanced-evaluation > .or-average-rating-inconsistency");
            showArgumentMAUTScore(inconsistencyLabel, requirement, data.messages, dataManager.ratingAttributeData,
                dataManager, (!hasCurrentUserAlreadyRated), false);
            this.bindUIEvents();
        }.bind(this));
        return false;
    }

    createCommentMessageDiv(requirementID, id, messageTitle, profileImageUrl, sentiment, assignedDimensions,
                            createdDate, shortFullName, isCurrentUser, conflictingCommentIDs, ratingAttributeIDMap) {
        conflictingCommentIDs = (conflictingCommentIDs === undefined) ? [] : conflictingCommentIDs;
	    var messageDiv = $("<div></div>").addClass("or-message or-comment-" + id);
	    var messageSenderDiv = $("<div></div>").addClass("or-message-sender");
	    var profileImage = $("<img>").attr("src", (profileImageUrl != null) ? profileImageUrl : "/images/userimage.png");
        profileImage.addClass("or-message-avatar or-avatar");
        profileImage.attr("alt", "Firstname Lastname");
        messageSenderDiv.append($("<div></div>").append(profileImage));
        messageSenderDiv.append($("<div></div>").addClass("or-message-sender-name").text(shortFullName));
        messageDiv.append(messageSenderDiv);

        var messageBodyDiv = $("<div></div>").addClass("or-message-body");
        var sentimentClass = "";

        if (sentiment == "PRO") {
            sentimentClass = "or-message-content-pro ";
        } else if (sentiment == "CON") {
            sentimentClass = "or-message-content-con ";
        } else if (sentiment == "NEUTRAL") {
            sentimentClass = "or-message-content-neutral ";
        }

        var messageContentDiv = $("<div></div>").addClass(sentimentClass + "or-message-content").append($("<span></span>").text(messageTitle));
        messageBodyDiv.append(messageContentDiv);
        var since = moment(formatDateTime(createdDate), "YYYY-MM-DD H:m:s").fromNow();
        var spanAssignedDimensions = $("<span></span>").addClass("or-message-assigned-dimensions-icon-bar");
        for (var i in assignedDimensions) {
            var ratingAttribute = ratingAttributeIDMap[assignedDimensions[i]];
            var dimensionIcon = $("<i></i>")
                .addClass("or-rating-attribute-icon material-icons")
                .text(ratingAttribute.iconName)
                .attr("data-toggle", "tooltip")
                .attr("data-placement", "bottom")
                .attr("title", ratingAttribute.name);
            spanAssignedDimensions.append(dimensionIcon);
        }

        if (conflictingCommentIDs.length > 0) {
            var dimensionIcon = $("<i></i>")
                .addClass("or-rating-attribute-icon material-icons")
                .text("flash_on")
                .attr("data-toggle", "tooltip")
                .attr("data-placement", "bottom")
                .attr("title", "This argument is in conflict with " +
                    conflictingCommentIDs.length + " other argument" +
                    (conflictingCommentIDs.length != 1 ? "s." : "."))
                .css("color", "#B90000");
            spanAssignedDimensions.append(dimensionIcon);
        }

        var messageSeenDiv = $("<div></div>").addClass("or-message-seen")
            .append($("<span></span>").text(since))
            .append(spanAssignedDimensions);
        messageBodyDiv.append(messageSeenDiv);
        messageDiv.append(messageBodyDiv);

        if (isCurrentUser) {
            var closeIcon = $("<i></i>")
                    .addClass("material-icons")
                    .text("close")
                    .attr("data-toggle", "tooltip")
                    .attr("data-placement", "bottom")
                    .attr("title", "Delete comment");

            var aRemove = $("<a></a>")
                .attr("href", "#")
                .attr("data-comment-id", id)
                .attr("data-requirement-id", requirementID)
                .attr("data-sentiment-type", sentiment)
                .addClass("or-delete-comment-btn")
                .append(closeIcon);
            messageDiv.append($("<div></div>").addClass("or-message-remove").append(aRemove));
        }

        messageDiv.append($("<div></div>").addClass("clear"));
        return messageDiv;
    }

    messageAssignmentClickEvent(event, thisObj) {
        thisObj.blur();
        var requirementID = parseInt($(".or-message-area-container").attr("data-requirement-ID"));
        var requirement = this.uiManager.dataManager.requirementData.filter(function (req) { return req.id == requirementID; })[0];
        var newMessageTextField = $(thisObj).parent().parent().children(".input-field").children(".or-new-message-textarea");
        var messageTitle = newMessageTextField.val();
        newMessageTextField.val("");

        if (messageTitle == "") {
            swal("Error", "Please enter a description for this argument!", "error").then(function (result) {
                this.messageClickEvent(event, thisObj, requirementID);
            }.bind(this));
            return false;
        }

        if ($("input[name='or-sentiment']:checked").val() === undefined) {
            swal("Error", "Please define the sentiment of this argument! You can either choose PRO, CON, or NEUTRAL.", "error").then(function (result) {
                this.messageClickEvent(event, thisObj, requirementID);
            }.bind(this));
            return false;
        }

        var sentiment = parseInt($("input[name='or-sentiment']:checked").val());
        $("input[name='or-sentiment']").prop("checked", false);

        $(".or-message-submit").attr("data-message-title", messageTitle);
        $(".or-message-submit").attr("data-sentiment", sentiment);
        $(".or-new-message").hide();
        $(".or-new-message-assignment").fadeIn();
        $(".or-argument-list-row").hide();
        $(".or-select-dimension-menu").css("border", "none");
        $(".or-no-dimension-selected-error").hide();
        $(".or-select-dimension-menu .or-checkbox").each(function () {
            $(this).prop("checked", false);
        });
        this.bindUIEvents();
        return false;
    }

    messageSendClickEvent(event, thisObj) {
        thisObj.blur();
        var projectID = this.uiManager.projectID;
        var uiEventHandler = this;
        var uiManager = this.uiManager;
        var dataManager = uiManager.dataManager;
        var currentUserID = dataManager.currentUserID;
        var requirementID = parseInt($(".or-message-area-container").attr("data-requirement-ID"));
        var requirement = this.uiManager.dataManager.requirementData.filter(function (req) { return req.id == requirementID; })[0];
        var messageTitle = $(".or-message-submit").attr("data-message-title");
        var sentiment = parseInt($(".or-message-submit").attr("data-sentiment"));
        var assignedDimensions = [];
        $(".or-select-dimension-menu .or-checkbox:checked").each(function () {
            assignedDimensions.push(parseInt($(this).attr("data-dimension")));
        });

        if (assignedDimensions.length == 0) {
            $(".or-select-dimension-menu").css("border", "2px #FF0000 solid");
            $(".or-no-dimension-selected-error").fadeIn();
            return false;
        }

        var jsonifiedString = JSON.stringify({
            title: messageTitle,
            message: "",
            sentiment: sentiment,
            assignedDimensions: assignedDimensions
        });

        var uiEventHandler = this;
        $.ajax("/project/" + projectID + "/requirement/" + requirementID + "/message/create.json", {
            'data': jsonifiedString,
            'type': 'POST',
            'contentType': 'application/json',
            'processData': false,
            'success': function (result) {
                $(".or-new-message").show();
                $(".or-new-message-assignment").hide();
                $(".or-argument-list-row").fadeIn();

                if (result.error == true) {
                    swal("Error", result.errorMessage, "error");
                    return false;
                }

                var id = result.id;
                var shortFullName = result.userData.shortFullName;
                var profileImagePath = result.userData.profileImagePath;
                var isCurrentUser = (result.userData.userID == currentUserID);
                var sentimentString = null;
                var proConIndicator = $("#or-requirement-" + requirementID + " .or-procon-indicator");
                var numberOfPros = parseInt(proConIndicator.attr("data-pro-count"));
                var numberOfNeus = parseInt(proConIndicator.attr("data-neu-count"));
                var numberOfCons = parseInt(proConIndicator.attr("data-con-count"));

                if (sentiment == 1) {
                    sentimentString = "PRO";
                    ++numberOfPros;
                } else if (sentiment == 2) {
                    sentimentString = "CON";
                    ++numberOfCons;
                } else if (sentiment == 0) {
                    sentimentString = "NEUTRAL";
                    ++numberOfNeus;
                }

                /*
                var newMessageDiv = uiEventHandler.createCommentMessageDiv(requirementID, id, messageTitle, profileImagePath, sentimentString, new Date(), shortFullName, isCurrentUser);
                newMessageDiv.hide();
                $(".or-message-container").prepend(newMessageDiv);
                newMessageDiv.fadeIn();
                */
                if (!(requirementID in dataManager.requirementMessages)) {
                    dataManager.requirementMessages[requirementID] = [];
                }
                $(".or-checkbox[data-dimension=" + assignedDimensions[0] + "]")
                    .attr("checked", "checked")
                    .prop("checked", true);
                $(".or-filter-conflict-menu:last .or-checkbox")
                    .removeAttr("checked")
                    .prop("checked", false);
                dataManager.requirementMessages[requirementID].push(result);
                requirement.messages = dataManager.requirementMessages[requirementID];
                requirement.advancedConflicts = result.conflicts;
                uiEventHandler.renderCommentListEvent(event, thisObj);
                var inconsistencyLabel = $("#or-requirement-" + requirementID + " > .or-requirement-advanced-evaluation > .or-average-rating-inconsistency");
                showArgumentMAUTScore(inconsistencyLabel, requirement, dataManager.requirementMessages[requirementID],
                    dataManager.ratingAttributeData, dataManager, true, true);

                var commentCountDiv = $("#or-requirement-" + requirementID + " .or-comment-count");
                var numberOfComments = parseInt(commentCountDiv.attr("data-comment-count")) + 1;
                commentCountDiv.attr("data-comment-count", numberOfComments);
                commentCountDiv.text(numberOfComments + " comment" + ((numberOfComments != 1) ? "s" : ""));
                updateProConIndicator(proConIndicator, numberOfPros, numberOfNeus, numberOfCons);
                uiEventHandler.bindUIEvents();
            }
        });
        return false;
    }

    deleteCommentClickEvent(event, thisObj) {
        var projectID = this.uiManager.projectID;
	    var requirementID = $(thisObj).attr("data-requirement-id");
        var requirement = this.uiManager.dataManager.requirementData.filter(function (req) { return req.id == requirementID; })[0];
	    var commentID = $(thisObj).attr("data-comment-id");
        var commentDiv = $(".or-comment-" + commentID);
        var sentiment = $(thisObj).attr("data-sentiment-type");
        var dataManager = this.uiManager.dataManager;

        var uiEventHandler = this;
        $.ajax("/project/" + projectID + "/requirement/" + requirementID + "/message/" + commentID + "/delete.json", {
            'type': 'POST',
            'success': function (result) {
                if (result.error == true) {
                    swal("Error", result.errorMessage, "error");
                    return false;
                }

                var remainingComments = dataManager.requirementMessages[requirementID].filter(function (m) { return m.id != commentID; });
                dataManager.requirementMessages[requirementID] = remainingComments;
                requirement.messages = dataManager.requirementMessages[requirementID];
                requirement.advancedConflicts = result.conflicts;
                var inconsistencyLabel = $("#or-requirement-" + requirementID + " > .or-requirement-advanced-evaluation > .or-average-rating-inconsistency");
                showArgumentMAUTScore(inconsistencyLabel, requirement, dataManager.requirementMessages[requirementID],
                    dataManager.ratingAttributeData, dataManager, true, true);

                var proConIndicator = $("#or-requirement-" + requirementID + " .or-procon-indicator");
                var numberOfPros = parseInt(proConIndicator.attr("data-pro-count"));
                var numberOfNeus = parseInt(proConIndicator.attr("data-neu-count"));
                var numberOfCons = parseInt(proConIndicator.attr("data-con-count"));

                if (sentiment == "PRO") {
                    --numberOfPros;
                } else if (sentiment == "CON") {
                    --numberOfCons;
                } else if (sentiment == "NEUTRAL") {
                    --numberOfNeus;
                }

                commentDiv.fadeOut("slow", "swing", function () {
                    commentDiv.remove();
                    uiEventHandler.renderCommentListEvent(event, thisObj);
                });
                var commentCountDiv = $("#or-requirement-" + requirementID + " .or-comment-count");
                var numberOfComments = parseInt(commentCountDiv.attr("data-comment-count")) - 1;
                commentCountDiv.attr("data-comment-count", numberOfComments);
                commentCountDiv.text(numberOfComments + " comment" + ((numberOfComments != 1) ? "s" : ""));
                updateProConIndicator(proConIndicator, numberOfPros, numberOfNeus, numberOfCons);
            }
        });
	    return false;
    }

    selectRateEvent(event, thisObj) {
        var requirementID = parseInt($(thisObj).parent().attr("id").split("-")[2]);
        var requirement = this.uiManager.dataManager.requirementData.filter(function (req) { return req.id == requirementID; })[0];
        $(".or-rating-self-vote").attr("data-requirement-id", requirementID);
        $(".or-rating-delegate-vote").attr("data-requirement-id", requirementID);
        var ratingSelectionContainer = $(".or-rating-selection-container");
        var ratingSelectionContainerContent = ratingSelectionContainer.wrap('<p/>').parent().html();
        ratingSelectionContainer.unwrap();
        var uiEventHandler = this;
        uiEventHandler.rateEvent(event, thisObj, false);
        var showRating = (requirement.yourRating != null) && (Object.keys(requirement.yourRating).length > 0);
        var isPrivateProject = this.uiManager.dataManager.projectData.isPrivateProject;
        var isVoteDelegated = ((requirement.yourRatingDelegation != null) && (Object.keys(requirement.yourRatingDelegation).length > 0));

        if (isVoteDelegated && isPrivateProject) {
            this.rateEvent(event, thisObj, false);
            return false;
        }

        if (showRating || !isPrivateProject) {
        	this.rateEvent(event, thisObj, false);
        	return false;
		}

        swal({
            html: '<div class="or-rating-area">'
            + ratingSelectionContainerContent
            + '</div>',
            customClass: "or-modal-wide",
            showConfirmButton: false,
            showCancelButton: true,
            //confirmButtonText: showRatingTable ? "OK" : "Close",
            cancelButtonText: "Cancel"
        });
        this.bindUIEvents();
        return false;
    }

	basicRequirementEvaluationEvent(event, thisObj, forceShowRatingTable) {
        var requirementID = parseInt($(thisObj).parent().attr("id").split("-")[2]);
		var requirement = this.uiManager.dataManager.requirementData.filter(function (req) { return req.id == requirementID; })[0];
		var isPrivateProject = this.uiManager.dataManager.projectData.isPrivateProject;
		var uiManager = this.uiManager;
		var uiEventHandler = this;
		var dataManager = uiManager.dataManager;
		var inconsistencyLabel = null;
		var userBasicRatings = requirement.userBasicRatings;
		var ratingArea = $(".or-basic-rating-area");
		var ratingOverviewTable = $(".or-rating-overview-table");
        var yourBasicRating = (requirement.yourBasicRating != null) ? requirement.yourBasicRating : 0;
        ratingArea.children().remove();

        $(".or-rating-anonymous-username-area").hide();
		var showRatingTable = forceShowRatingTable || (requirement.yourBasicRating == 0);

		if (showRatingTable) {
			ratingArea.show();
			ratingOverviewTable.hide();
			var select = $("<select></select>").addClass("or-basic-rating-field");
			for (var level = 1; level <= 5; ++level) {
                select.append($("<option></option>").attr("value", level).text(level));
            }
			ratingArea.append(select);

            if (!isPrivateProject) {
				$(".or-rating-anonymous-username-area").show();
			}
        } else {
			ratingArea.hide();
			ratingOverviewTable.show();

			var thead = $(".or-rating-overview-table > thead");
			thead.children("tr").remove();
			var tr = $("<tr></tr>");
			tr.append($("<th></th>"));

            var th = $("<th></th>").addClass("center").text("Rating");
            tr.append(th);
			thead.append(tr);

			var tbody = $(".or-rating-overview-table > tbody");
			tbody.children("tr").remove();

			var ratingSum = 0.0;
			var ratingCount = 0;
			for (var userID in userBasicRatings) {
				var userRating = userBasicRatings[userID];
				if (!("value" in userRating)) {
					continue;
				}

				var tr = $("<tr></tr>");
				var personName = isPrivateProject ? (userRating.userFirstName + " " + userRating.userLastName) : userID;
				var isRatingOfCurrentUser = (userID == dataManager.currentUserID);
				var td = $("<td></td>").append($("<div></div>").text(personName));

				if (isRatingOfCurrentUser) {
					var editIcon = $("<i></i>").addClass("material-icons right").text("edit");
					td.append($("<a></a>").attr("href", "#").addClass("or-rating-edit").append(editIcon));
				}
				tr.append(td);

                var td = $("<td></td>").addClass("center");
                var ratingValue = userRating.value;

                var ratingValueDiv = $("<div></div>").addClass("or-basic-rating-list-item");
                var select = $("<select></select>")
                    .addClass("or-basic-rating-list-field")
                    .attr("data-current-rating", ratingValue);

                for (var level = 1; level <= 5; ++level) {
                    select.append($("<option></option>").attr("value", level).text(level));
                }
                ratingValueDiv.append(select);
                ratingValueDiv.append($("<span></span>").text(ratingValue));
                td.append(ratingValueDiv);
                tr.append(td);
				tbody.append(tr);

                ratingSum += userRating.value;
                ++ratingCount;
            }

			tr = $("<tr></tr>").addClass("or-rating-average-row");
			inconsistencyLabel = $("<span></span>").addClass("or-average-rating-table-overview-inconsistency");
			inconsistencyLabel.hide();
            tr.append($("<td></td>").append("Average").append(inconsistencyLabel));

            var average = (ratingCount > 0) ? (ratingSum / ratingCount) : 0.0;
            var select = $("<select></select>")
                .addClass("or-basic-rating-average-field")
                .attr("data-current-rating", average.toFixed(1));

            for (var level = 1; level <= 5; ++level) {
                select.append($("<option></option>").attr("value", level).text(level));
            }

            var td = $("<td></td>").addClass("center");
            var ratingValueDiv = $("<div></div>");
            ratingValueDiv.append(select);
            ratingValueDiv.append($("<span></span>").text(average.toFixed(2)));
            td.append(ratingValueDiv);
            tr.append(td);
			tbody.append(tr);
		}

        var ratingContainer = $(".or-basic-rating-container");
		ratingContainer.children(".or-basic-rating-subtitle").text(requirement.title);
		var ratingContainerContent = ratingContainer.wrap('<p/>').parent().html();
		ratingContainer.unwrap();

		swal({
			html: '<div class="or-rating-area">'
				+ ratingContainerContent
				+ '</div>',
				customClass: showRatingTable ? "or-modal-normal" : "or-modal-wide",
				showCancelButton: showRatingTable,
				confirmButtonText: showRatingTable ? "OK" : "Close",
				cancelButtonText: "Cancel"
		}).then(function (result) {
            if (("value" in result) && result.value) {
                if (showRatingTable && result.value) {
                    uiEventHandler.saveBasicRequirementEvaluationEvent(event, thisObj);
                }
            } else if (forceShowRatingTable == true) {
                uiEventHandler.basicRequirementEvaluationEvent(event, thisObj, null);
            }
            return true;
        });

        $(".or-basic-rating-field, .or-basic-rating-list-field").barrating("show", {
            theme: 'fontawesome-stars-o',
            allowEmpty: true,
            showSelectedRating: true,
            showValues: false,
            deselectable: true
        }).barrating("clear");

        $(".or-basic-rating-average-field").barrating("show", {
            theme: 'fontawesome-stars-o',
            initialRating: $(".or-basic-rating-average-field").attr("data-current-rating"),
            allowEmpty: true,
            showSelectedRating: true,
            showValues: false,
            deselectable: true
        }).barrating("readonly", true);

        $(".or-basic-rating-list-field").barrating("readonly", true);
        $(".or-basic-rating-list-field").each(function () {
            var ratingValue = $(this).attr("data-current-rating");
            $(this).barrating("set", ratingValue);
        });

        $(".or-rating-edit").on("click", function () {
			uiEventHandler.basicRequirementEvaluationEvent(event, thisObj, true);
			return false;
		});

		if (yourBasicRating > 0) {
            $(".or-basic-rating-field").last().barrating("set", yourBasicRating);
        }

		if (inconsistencyLabel != null) {
		    showBasicRatingConflict($(".or-average-rating-table-overview-inconsistency").last(), userBasicRatings);
		}

        this.bindUIEvents();
		return false;
	}

    deleteRatingAttributeClickEvent(event, thisObj) {
        var projectID = this.uiManager.projectID;
        var ratingAttributeID = parseInt($(thisObj).attr("data-rating-attribute-id"));

        var uiEventHandler = this;
        var ratingAttributeRow = $(thisObj).parent("td").parent("tr");
        $.ajax("/project/" + projectID + "/rating/attribute/" + ratingAttributeID + "/delete.json", {
            'type': 'GET',
            'contentType': 'application/json',
            'processData': false,
            'success': function (result) {
                if (result.error == true) {
                    swal("Error", result.errorMessage, "error");
                    return false;
                }

                ratingAttributeRow.fadeOut();
                uiEventHandler.bindUIEvents();
            }
        });
	    return false;
    }

	rateEvent(event, thisObj, forceShowRatingTable) {
        var requirementID = parseInt($(thisObj).parent().attr("id").split("-")[2]);
        //var requirementID = parseInt($(".or-rating-self-vote").attr("data-requirement-id"));
		var requirement = this.uiManager.dataManager.requirementData.filter(function (req) { return req.id == requirementID; })[0];
		var isPrivateProject = this.uiManager.dataManager.projectData.isPrivateProject;
		var uiManager = this.uiManager;
		var uiEventHandler = this;
		var dataManager = uiManager.dataManager;
		var nameOfAnonymousUser = getCookie("nameOfAnonymousUser");
		var inconsistencyLabel = null;
		var ratings = isPrivateProject ? requirement.userRatings : requirement.anonymousRatings;
		var ratingTable = $(".or-rating-table");
		var ratingOverviewTable = $(".or-rating-overview-table");
        $(".or-basic-rating-subtitle").text(requirement.title);

        $(".or-rating-anonymous-username-area").hide();
		var showRatingTable = forceShowRatingTable || (requirement.yourRating == null) || (Object.keys(requirement.yourRating).length == 0);
		if ((requirement.yourRatingDelegation != null) && (Object.keys(requirement.yourRatingDelegation).length > 0)) {
		    showRatingTable = false;
        }

		var extendedModalWidth = false;
		if (showRatingTable) {
			ratingTable.show();
			ratingOverviewTable.hide();

			if (!isPrivateProject) {
				$(".or-rating-anonymous-username-area").show();
			}

			var tbody = $(".or-rating-table > tbody");
			tbody.children("tr").remove();

			for (var i in dataManager.ratingAttributeData) {
				var ratingAttribute = dataManager.ratingAttributeData[i];
				var ratingAttriuteIcon = $("<i></i>").addClass("material-icons left").text(ratingAttribute.iconName);
				var ratingAttributeValueField = $("<select></select>").addClass("or-rating-field or-rating-field-" + ratingAttribute.id)
                                                                      .attr("name", "or-rating-" + ratingAttribute.id)
                                                                      .attr("data-rating-attribute-id", ratingAttribute.id)
                                                                      .attr("autocomplete", "off");

				ratingAttributeValueField.append($("<option></option>").attr("value", "").text(""));

				for (var i = ratingAttribute.minValue; i <= ratingAttribute.maxValue; i++) {
                    var option = $("<option></option>").attr("value", i);
                    if (ratingAttribute.name.toLowerCase() == "effort") {
                        var hours = Math.pow(2, (i - 1));
                        option.text(hours + "h").attr("data-html", hours + "h");
                    } else {
                        option.text(i);
                    }
                    ratingAttributeValueField.append(option);
				}

				var tr = $("<tr></tr>");
				var ratingAttributeNameColumn = $("<td></td>").addClass("or-rating-attribute-name-cell").append(ratingAttriuteIcon).append(" " + ratingAttribute.name);
				var ratingAttributeDescriptionColumn = $("<td></td>").addClass("or-rating-attribute-description-cell").append(ratingAttribute.description);
				var ratingAttributeValueColumn = $("<td></td>")
                    .addClass("or-rating-field-cell")
                    //.attr("data-attribute-name", ratingAttribute.name.toLowerCase())
                    .append(ratingAttributeValueField);

				tr.append(ratingAttributeNameColumn);
				tr.append(ratingAttributeDescriptionColumn);
				tr.append(ratingAttributeValueColumn);
				tbody.append(tr);
			}
		} else {
			ratingTable.hide();
			ratingOverviewTable.show();

			var thead = $(".or-rating-overview-table > thead");
			thead.children("tr").remove();
			var tr = $("<tr></tr>");
			tr.append($("<th></th>"));

			for (var i in dataManager.ratingAttributeData) {
				var ratingAttribute = dataManager.ratingAttributeData[i];
				var ratingAttriuteIcon = $("<i></i>").addClass("or-rating-attribute-icon material-icons left").text(ratingAttribute.iconName);
				var th = $("<th></th>");
				th.append(ratingAttriuteIcon);
				th.append("" + ratingAttribute.name);
				tr.append(th);
			}

			thead.append(tr);

			var tbody = $(".or-rating-overview-table > tbody");
			tbody.children("tr").remove();

			var averageCounts = {};
			var ratingCounts = {};
			for (var userID in ratings) {
				var userRatings = ratings[userID];
				if (userRatings.length == 0) {
					continue;
				}

				var tr = $("<tr></tr>");
				var personName = isPrivateProject ? (userRatings[0].userFirstName + " " + userRatings[0].userLastName) : userID;
				var isRatingOfCurrentUser = isPrivateProject ? (userID == dataManager.currentUserID) : (userID == nameOfAnonymousUser);
				var td = $("<td></td>").append($("<div></div>").text(personName));
                var userWeight = computeUserLiquidDemocracyDelegationImportance(requirement.ratingBackwardDelegations, userID);

                if (userWeight > 1) {
                    var numberOfDelegations = (userWeight - 1);
                    var label = $("<div></div>")
                        .addClass("or-shared-users-table-email")
                        .text(" " + numberOfDelegations + " other vote" + ((numberOfDelegations != 1) ? "s were" : " was") + " delegated to this user");
                    td.append(label);
                }

				if (isRatingOfCurrentUser) {
					var editIcon = $("<i></i>").addClass("material-icons right").text("edit");
					td.append($("<a></a>").attr("href", "#").addClass("or-rating-edit").append(editIcon));
				}
				tr.append(td);

                for (var i in dataManager.ratingAttributeData) {
					var ratingAttribute = dataManager.ratingAttributeData[i];
					var td = $("<td></td>").addClass("center");
					var ratingValue = "-";
					for (var j in userRatings) {
						var userRating = userRatings[j];
						if (ratingAttribute.id == userRating.attributeID) {
							if (!(ratingAttribute.id in averageCounts)) {
								averageCounts[ratingAttribute.id] = 0.0;
								ratingCounts[ratingAttribute.id] = 0;
							}
                            if (ratingAttribute.name.toLowerCase() == "effort") {
                                var hours = Math.pow(2, (userRating.value - 1));
                                ratingValue = userRating.value + " (" + hours + "h)";
                            } else {
                                ratingValue = userRating.value;
                            }
                            averageCounts[ratingAttribute.id] += userRating.value;
							++ratingCounts[ratingAttribute.id];
							break;
						}
					}
					td.append(ratingValue);
					tr.append(td);
				}
				tbody.append(tr);
			}

			tr = $("<tr></tr>").addClass("or-rating-average-row");
			inconsistencyLabel = $("<span></span>").addClass("or-average-rating-table-overview-inconsistency");
			inconsistencyLabel.hide();
			tr.append($("<td></td>").append("Average").append(inconsistencyLabel));
			for (var i in dataManager.ratingAttributeData) {
				var ratingAttribute = dataManager.ratingAttributeData[i];
				var average = (ratingAttribute.id in averageCounts) ? (averageCounts[ratingAttribute.id] / ratingCounts[ratingAttribute.id]) : null;
				tr.append($("<td></td>").addClass("center").text((average != null) ? average.toFixed(2) : "-"));
			}
			tbody.append(tr);
            extendedModalWidth = true;
        }

        var isVoteDelegated = ((requirement.yourRatingDelegation != null) && (Object.keys(requirement.yourRatingDelegation).length > 0));
		if (isVoteDelegated) {
            var tr = this.createUserTableRow(requirement.yourRatingDelegation, "Your vote was delegated to this user.", false, false,
                "or-remove-delegation-btn", false, { "data-requirement-id": requirementID });
            $(".or-stakeholder-delegate-table > tbody").children().remove();
            $(".or-stakeholder-delegate-table > tbody").append(tr);
            $(".or-stakeholder-delegate-container").show();
        } else {
            $(".or-stakeholder-delegate-container").hide();
        }

        var ratingContainer = $(".or-rating-container");
		var ratingContainerContent = ratingContainer.wrap('<p/>').parent().html();
		ratingContainer.unwrap();

		swal({
			html: '<div class="or-rating-area">'
				+ ratingContainerContent
				+ '</div>',
				customClass: showRatingTable ? "or-modal-normal" : "or-modal-wide",
				showCancelButton: showRatingTable,
				confirmButtonText: showRatingTable ? "OK" : "Close",
				cancelButtonText: "Cancel"
		}).then(function (result) {
            if (("value" in result) && result.value) {
                if (showRatingTable && result.value) {
                    uiEventHandler.saveRatingEvent(event, thisObj);
                }
            } else if (forceShowRatingTable == true) {
                uiEventHandler.rateEvent(event, thisObj, null);
            }
            return true;
        });

		if (extendedModalWidth) {
            if (dataManager.ratingAttributeData.length >= 5) {
                $(".swal2-popup").css("width", "800px");
            } else if (dataManager.ratingAttributeData.length > 3) {
                $(".swal2-popup").css("width", "600px");
            }
        }

		$(".or-rating-edit").on("click", function () {
			uiEventHandler.rateEvent(event, thisObj, true);
			return false;
		});

		$(".or-rating-field").barrating('show', {
			theme: 'bars-1to10',
	        allowEmpty: true,
	        showSelectedRating: true,
	        showValues: false,
	        deselectable: true,
            onSelect: function(value, text, event) {
			    //console.log(value);
	        }
		}).barrating('clear');

		if (requirement.yourRating != null) {
			for (var ratingAttributeID in requirement.yourRating) {
				var value = requirement.yourRating[ratingAttributeID];
				if (value > 0) {
					$(".or-rating-field-" + ratingAttributeID).last().barrating("set", value);
				}
			}
		}

		if (nameOfAnonymousUser !== undefined) {
			$(".or-rating-anonymous-username").prop('disabled', true);
			$(".or-rating-anonymous-username").val(nameOfAnonymousUser);
		} else {
			$(".or-rating-anonymous-username").prop('disabled', false);
			$(".or-rating-anonymous-username").focus();
		}

		if (inconsistencyLabel != null) {
            showRatingConflict($(".or-average-rating-table-overview-inconsistency").last(),
                dataManager.ratingAttributeData, ratings);
		}

		this.bindUIEvents();
		return false;
	}

    rateDelegateEvent(event, thisObj, requirementID) {
        requirementID = (requirementID === undefined) ? parseInt($(thisObj).attr("data-requirement-id")) : requirementID;
        var ratingDelegateContainer = $(".or-stakeholder-delegate-user-container");
        $(".or-delegate-vote-user-btn").attr("data-requirement-id", requirementID);

        var ratingDelegateContainerContent = ratingDelegateContainer.wrap('<p/>').parent().html();
        ratingDelegateContainer.unwrap();

        swal({
            html: '<div class="or-rating-area">'
            + ratingDelegateContainerContent
            + '</div>',
            customClass: "or-modal-wide",
            showConfirmButton: false,
            showCancelButton: true,
            cancelButtonText: "Close"
        });
        this.bindUIEvents();
        return false;
    }

    rateDelegateClickEvent(event, thisObj) {
        var searchField = $(".or-search-user:last");
        var requirementID = parseInt($(thisObj).attr("data-requirement-id"));
        var dataManager = this.uiManager.dataManager;
        var requirement = dataManager.requirementData.filter(function (req) { return req.id == requirementID; })[0];
        var projectID = this.uiManager.projectID;
        var uiManager = this.uiManager;
        var currentUserID = uiManager.dataManager.currentUserID;

        if (searchField.val() == "") {
            swal("Error", "Please enter a valid name of a user!", "error").then(function (result) {
                this.rateDelegateEvent(event, thisObj);
            }.bind(this));
            return false;
        }

        $(".or-search-user").prop('disabled', true);
        $(".or-delegate-vote-user-btn").prop('disabled', true);
        $(".or-delegate-vote-loading-animation").show();

        var uiEventHandler = this;
        $.ajax("/project/" + projectID + "/requirement/" + requirementID + "/vote/delegate.json", {
            'data': { query: searchField.val() },
            'type': 'POST',
            'success': function (result) {
                $(".or-search-user").prop('disabled', false);
                $(".or-delegate-vote-user-btn").prop('disabled', false);
                $(".or-delegate-vote-loading-animation").hide();

                if (result.error == true) {
                    searchField.val("").focus();
                    swal("Error", result.errorMessage, "error").then(function (result) {
                        uiEventHandler.rateDelegateEvent(event, thisObj);
                    });
                    return false;
                }

                requirement.yourRatingDelegation = result.userData;
                requirement.ratingForwardDelegations[currentUserID] = result.userData;

                var userIDOfDelegatedUser = result.userData.id;
                if (!(userIDOfDelegatedUser in requirement.ratingBackwardDelegations)) {
                    requirement.ratingBackwardDelegations[userIDOfDelegatedUser] = [];
                }
                requirement.ratingBackwardDelegations[userIDOfDelegatedUser].push(result.currentUserData);
                showMAUTScore(requirement, requirement.userRatings, dataManager.ratingAttributeData, dataManager);
                uiEventHandler.rateEvent(event, thisObj, false);
                searchField.val("");
                uiEventHandler.bindUIEvents();
            }
        });
    }

    removeDelegationClickEvent(event, thisObj) {
        var projectID = this.uiManager.projectID;
        var requirementID = parseInt($(thisObj).attr("data-requirement-id"));
        var requirement = this.uiManager.dataManager.requirementData.filter(function (req) { return req.id == requirementID; })[0];
        var uiManager = this.uiManager;
        var currentUserID = uiManager.dataManager.currentUserID;
        var tr = $(thisObj).parent(".or-shared-users-table-remove-cell").parent("tr");

        $.ajax("/project/" + projectID + "/requirement/" + requirementID + "/vote/delegation/remove.json", {
            'type': 'GET',
            'success': function (result) {
                if (result.error == true) {
                    swal("Error", result.errorMessage, "error");
                    return false;
                }

                var userIDOfDelegatedUser = requirement.yourRatingDelegation.id;
                requirement.yourRatingDelegation = null;
                delete requirement.ratingForwardDelegations[currentUserID];
                var backwardDelegations = requirement.ratingBackwardDelegations[userIDOfDelegatedUser];
                var newBackwardDelegations = [];

                for (var i = 0; i < backwardDelegations.length; i++) {
                    if (backwardDelegations[i].id != currentUserID) {
                        newBackwardDelegations.push(backwardDelegations[i]);
                    }
                }

                requirement.ratingBackwardDelegations[userIDOfDelegatedUser] = newBackwardDelegations;

                tr.fadeOut("slow", "swing", function () {
                    tr.remove();
                    $(".swal2-cancel").click();
                });
            }
        });
		return false;
	}

    assignStakeholderEvent(event, thisObj, requirementID) {
        thisObj.blur();
        $(".ui-tooltip").hide();
        requirementID = (requirementID === undefined) ? parseInt($(thisObj).parent().parent().parent().attr("id").split("-")[2]) : requirementID;
        var projectID = this.uiManager.projectID;
        var uiEventHandler = this;
        var uiManager = this.uiManager;
        var dataManager = uiManager.dataManager;
        var isPrivateProject = dataManager.projectData.isPrivateProject;
        var creatorUserID = dataManager.projectData.creatorUserID;
        var currentUserID = uiManager.dataManager.currentUserID;
        $(".or-assigned-stakeholders-table > tbody > tr:not(.or-assign-stakeholder-loading-animation):not(.or-assign-stakeholder-placeholder)").remove();
        $(".or-assign-stakeholder-loading-animation").hide();
        $(".or-assign-stakeholder-placeholder").show();
        $(".or-assigned-stakeholders-container").attr("data-requirement-ID", requirementID);

        $.getJSON("/project/" + projectID + "/requirement/" + requirementID + "/user/list.json", function(data) {
            if (data.assignedUsers != null) {
                for (var i in data.assignedUsers) {
                    $(".or-assign-stakeholder-placeholder").hide();
                    var fullName = data.assignedUsers[i].firstName + " " + data.assignedUsers[i].lastName;
                    var tr = this.createRequirementAssignedUserTableRow(requirementID, data.assignedUsers[i].id,
                        data.assignedUsers[i].isAccepted, data.assignedUsers[i].isHidden, data.assignedUsers[i].proposedBy,
                        false, dataManager.stakeholderRatingAttributeData,
                        data.assignedUsers[i].stakeholderVotes, fullName,
                        data.assignedUsers[i].profileImagePath, (data.assignedUsers[i].id == currentUserID),
                        currentUserID, creatorUserID);
                    $(".or-assigned-stakeholders-table > tbody").prepend(tr);
                }
            }

            if (data.assignedAnonymousUsers != null) {
                for (var i in data.assignedAnonymousUsers) {
                    $(".or-assign-stakeholder-placeholder").hide();
                    var tr = this.createRequirementAssignedUserTableRow(requirementID, data.assignedAnonymousUsers[i].id,
                        data.assignedAnonymousUsers[i].isAccepted, data.assignedAnonymousUsers[i].isHidden, -1,
                        true, dataManager.stakeholderRatingAttributeData, data.assignedAnonymousUsers[i].stakeholderVotes,
                        data.assignedAnonymousUsers[i].fullName, null, false, currentUserID, creatorUserID);
                    $(".or-assigned-stakeholders-table > tbody").prepend(tr);
                }
            }

            if (!isPrivateProject) {
                $(".or-rating-specific-content").hide();
            }
            var assignedUsersContainer = $(".or-assigned-stakeholders-container");
            var assignedUsersContainerContent = assignedUsersContainer.wrap('<p/>').parent().html();
            assignedUsersContainer.unwrap();
            this.showAssignStakeholderDialogEvent(assignedUsersContainerContent, isPrivateProject);
            uiEventHandler.initializeBarRating();
        }.bind(this));
        return false;
    }

    requirementQualityClickEvent(event, thisObj) {
        var uiManager = this.uiManager;
        var dataManager = uiManager.dataManager;
        var requirementID = parseInt($(thisObj).attr("data-requirement-id"));
        var requirementTitle = $("#or-requirement-" + requirementID + " input.or-requirement-title").val();
        var requirementDescription = $("#or-requirement-" + requirementID + " div.or-requirement-description").summernote("code");
        requirementDescription = requirementDescription.replace(/<\/?[^>]+(>|$)/g, "");

        if (requirementTitle === undefined || requirementDescription === undefined || requirementTitle == "" || requirementDescription == "") {
            swal("Error", "Please enter a title and description first!", "error");
            return false;
        }

        var classMapping = {
            "Passive Ambiguity": "passive",
            "Pronoun": "pronoun",
            "Adverb": "adverb",
            "Optional": "optional",
            "Ambiguous Positioning": "ambiguous-positioning",
            "Ambiguous Nominalization": "ambiguous-nominalization",
            "Negative": "negative",
            "Adjective": "adjective",
            "Comparative Adjective": "comparative-adjective",
            "Inside Behaviour": "behaviour",
            "Ambiguous Compound Nouns": "compound-nouns",
            "Vague": "vague"
        };

        var ambiguityData = dataManager.ambiguityIssueData[requirementID];
        var currentIndex = 0;
        var highlightedRequirementDescription = "";
        var usedAmbiguities = {};
        for (var i in ambiguityData) {
            console.log(ambiguityData[i].title);
            usedAmbiguities[ambiguityData[i].title] = ambiguityData[i].description;
            var tooltipText = ambiguityData[i].title + ": " + ambiguityData[i].description;
            highlightedRequirementDescription += requirementDescription.slice(currentIndex, ambiguityData[i].index_start);
            highlightedRequirementDescription += "<span class=\"or-highlight-ambiguity or-highlight-ambiguity-"
                + classMapping[ambiguityData[i].title] + "\" data-toggle=\"tooltip\" data-placement=\"left\" title=\""
                + tooltipText + "\">";
            highlightedRequirementDescription += requirementDescription.slice(ambiguityData[i].index_start, ambiguityData[i].index_end);
            highlightedRequirementDescription += "</span>";
            currentIndex = ambiguityData[i].index_end;
        }

        highlightedRequirementDescription += requirementDescription.slice(currentIndex);

        var ambiguityContainer = $(".or-ambiguity-container");
        ambiguityContainer.children("h2").text(requirementTitle);
        ambiguityContainer.children("p").html(highlightedRequirementDescription);

        $(".or-ambiguity-explanation-list").children().remove();

        for (var ambiguityTitle in usedAmbiguities) {
            var divExplanation = $("<div></div>").addClass("or-ambiguity-explanation");
            var divExplanationTitle = $("<div></div>")
                .addClass("or-ambiguity-explanation-title")
                .addClass("or-highlight-ambiguity-" + classMapping[ambiguityTitle])
                .text(ambiguityTitle);
            var divExplanationDescription = $("<div></div>")
                .addClass("or-ambiguity-explanation-description")
                .text(usedAmbiguities[ambiguityTitle]);
            divExplanation.append(divExplanationTitle);
            divExplanation.append(divExplanationDescription);
            $(".or-ambiguity-explanation-list").append(divExplanation);
        }

        var ambiguityContainerContent = ambiguityContainer.wrap('<p/>').parent().html();
        ambiguityContainer.unwrap();

        swal({
            html:ambiguityContainerContent,
            showCancelButton: false,
            confirmButtonText: "Close"
        });

        $(".swal2-popup").css("width", "700px");
        $('[data-toggle="tooltip"]').tooltip();
        return false;
    }

    rateStakeholderClickEvent(event, thisObj) {
	    var requirementID = parseInt($(thisObj).attr("data-requirement-id"));
	    var userID = parseInt($(thisObj).attr("data-user-id"));
	    var isAnonymousUser = ($(thisObj).attr("data-is-anonymous-user") === "true");
	    var fullName = $(thisObj).attr("data-user-fullname");
	    var uiEventHandler = this;
        var uiManager = this.uiManager;
        var dataManager = uiManager.dataManager;

        var tbody = $(".or-stakeholder-rating-table > tbody");
        tbody.children("tr").remove();

        $(".or-stakeholder-rating-name").text(fullName);

        for (var i in dataManager.stakeholderRatingAttributeData) {
            var ratingAttribute = dataManager.stakeholderRatingAttributeData[i];
            var ratingAttriuteIcon = $("<i></i>").addClass("material-icons left").text(ratingAttribute.iconName);
            var ratingAttributeValueField = $("<select></select>").addClass("or-rating-field or-rating-field-" + ratingAttribute.id)
                .attr("name", "or-rating-" + ratingAttribute.id)
                .attr("data-rating-attribute-id", ratingAttribute.id)
                .attr("autocomplete", "off");

            ratingAttributeValueField.append($("<option></option>").attr("value", "").text(""));

            for (var i = ratingAttribute.minValue; i <= ratingAttribute.maxValue; i++) {
                ratingAttributeValueField.append($("<option></option>").attr("value", i).text(i));
            }

            var tr = $("<tr></tr>");
            var ratingAttributeNameColumn = $("<td></td>").addClass("or-rating-attribute-name-cell").append(ratingAttriuteIcon).append(" " + ratingAttribute.name);
            var ratingAttributeDescriptionColumn = $("<td></td>").addClass("or-rating-attribute-description-cell").append(ratingAttribute.description);
            var ratingAttributeValueColumn = $("<td></td>").addClass("or-rating-field-cell").append(ratingAttributeValueField);

            tr.append(ratingAttributeNameColumn);
            tr.append(ratingAttributeDescriptionColumn);
            tr.append(ratingAttributeValueColumn);
            tbody.append(tr);
        }

        var stakeholderRatingContainer = $(".or-stakeholder-rating-container");
        stakeholderRatingContainer.attr("data-requirement-id", requirementID);
        stakeholderRatingContainer.attr("data-user-id", userID);
        stakeholderRatingContainer.attr("data-is-anonymous-user", isAnonymousUser);
        var stakeholderRatingContainerContent = stakeholderRatingContainer.wrap('<p/>').parent().html();
        stakeholderRatingContainer.unwrap();

        swal({
            html: '<div class="or-rating-area">'
            + stakeholderRatingContainerContent
            + '</div>',
            customClass: "or-modal-normal",
            showCancelButton: true,
            confirmButtonText: "OK",
            cancelButtonText: "Cancel"
        }).then(function (result) {
            if (("value" in result) && result.value) {
                if (result.value) {
                    uiEventHandler.saveStakeholderRatingEvent(event, thisObj);
                }
            } else {
                uiEventHandler.assignStakeholderEvent(event, thisObj, requirementID);
            }
            return true;
        });

        $(".or-rating-field").barrating('show', {
            theme: 'bars-1to10',
            allowEmpty: true,
            showSelectedRating: true,
            showValues: false,
            deselectable: true,
            onSelect: function(value, text, event) {
                /*
                if (typeof(event) !== 'undefined') {
                  // rating was selected        by a user
                  console.log(event.target);
                } else {
                  // rating was selected programmatically
                  // by calling `set` method
                }
            */
            }
        }).barrating('clear');
    }

    saveStakeholderRatingEvent(event, thisObj) {
        var stakeholderRatingContainer = $(".or-stakeholder-rating-container");
        var requirementID = parseInt(stakeholderRatingContainer.attr("data-requirement-id"));
        var userID = parseInt(stakeholderRatingContainer.attr("data-user-id"));
        var isAnonymousUser = (stakeholderRatingContainer.attr("data-is-anonymous-user") === "true");

        var projectID = this.uiManager.projectID;
        var uiEventHandler = this;
        var dataManager = this.dataManager;
        var fields = $(".or-stakeholder-rating-table:last > tbody > tr > td.or-rating-field-cell > div > select.or-rating-field");

        var ratingAttributeIDMap = {};
        for (var i in dataManager.stakeholderRatingAttributeData) {
            var ratingAttributeInfo = dataManager.stakeholderRatingAttributeData[i];
            ratingAttributeIDMap[ratingAttributeInfo.id] = ratingAttributeInfo;
        }

        var attributeVotes = [];
        fields.each(function () {
            var attributeID = $(this).attr("data-rating-attribute-id");
            var value = parseFloat(($(this).val() != "") ? $(this).val() : 0.0);
            attributeVotes.push({ ratingAttributeID: attributeID, ratingValue: value });
        });

        var url = "/project/" + projectID + "/requirement/" + requirementID + "/stakeholder/vote.json";
        var jsonifiedString = JSON.stringify({
            ratedStakeholderID: userID,
            anonymousUser: isAnonymousUser,
            attributeVotes: attributeVotes
        });

        $.ajax(url, {
            'data': jsonifiedString,
            'type': 'POST',
            'contentType': 'application/json',
            'processData': false,
            'success': function (result) {
                if (result.error == true) {
                    swal("Error", result.errorMessage, "error");
                    return false;
                }

                uiEventHandler.assignStakeholderEvent(event, thisObj, requirementID);
            }
        });
        return false;
    }

    showAssignStakeholderDialogEvent(assignedUsersContainerContent, isRatingEnabled) {
        swal({
            html: '<div class="or-share-area">' + assignedUsersContainerContent + '</div>',
            showCancelButton: false,
            confirmButtonText: "Close"
        });
        if (isRatingEnabled) {
            $(".swal2-popup").css("width", "1000px");
        }
        this.bindUIEvents();
    }

    acceptStakeholderClickEvent(event, thisObj) {
        var projectID = this.uiManager.projectID;
        var requirementID = parseInt($(thisObj).attr("data-requirement-id"));
        var userID = parseInt($(thisObj).attr("data-user-id"));
        var isAnonymousUser = ($(thisObj).attr("data-is-anonymous-user") == "true");
        var uiEventHandler = this;

        $.ajax("/project/" + projectID + "/requirement/" + requirementID + "/user/" + userID + "/accept.json", {
            'data': { isAnonymousUser: isAnonymousUser },
            'type': 'POST',
            'success': function (result) {
                if (result.error == true) {
                    swal("Error", result.errorMessage, "error").then(function (result) {
                        uiEventHandler.assignStakeholderEvent(event, thisObj, requirementID);
                    });
                    return false;
                }

                uiEventHandler.assignStakeholderEvent(event, thisObj, requirementID);
            }
        });
	    return false;
    }

    unacceptStakeholderClickEvent(event, thisObj) {
        var projectID = this.uiManager.projectID;
        var requirementID = parseInt($(thisObj).attr("data-requirement-id"));
        var userID = parseInt($(thisObj).attr("data-user-id"));
        var isAnonymousUser = ($(thisObj).attr("data-is-anonymous-user") == "true");
        var uiEventHandler = this;

        $.ajax("/project/" + projectID + "/requirement/" + requirementID + "/user/" + userID + "/unaccept.json", {
            'data': { isAnonymousUser: isAnonymousUser },
            'type': 'POST',
            'success': function (result) {
                if (result.error == true) {
                    swal("Error", result.errorMessage, "error").then(function (result) {
                        uiEventHandler.assignStakeholderEvent(event, thisObj, requirementID);
                    });
                    return false;
                }

                uiEventHandler.assignStakeholderEvent(event, thisObj, requirementID);
            }
        });
        return false;
    }

    saveBasicRequirementEvaluationEvent(event, thisObj) {
        var requirementID = parseInt($(thisObj).parent().attr("id").split("-")[2]);
		var requirement = this.uiManager.dataManager.requirementData.filter(function (req) { return req.id == requirementID; })[0];
		var projectID = this.uiManager.projectID;
		var uiEventHandler = this;
		var dataManager = this.dataManager;
		var field = $(".or-basic-rating-field:last");

		var newYourRatingDataDetailed = {};
        var value = parseFloat((field.val() != "") ? field.val() : 0);
        if (value != 0) {
            newYourRatingDataDetailed["value"] = value;
            newYourRatingDataDetailed["userFirstName"] = dataManager.userData.userFirstName;
            newYourRatingDataDetailed["userLastName"] = dataManager.userData.userLastName;
            newYourRatingDataDetailed["userEmail"] = dataManager.userData.userEmail;
        }

		var url = "/project/" + projectID + "/requirement/basic/vote.json";
		var jsonifiedString = JSON.stringify({
			requirementID: requirementID,
            ratingValue: value
		});

		$.ajax(url, {
		    'data': jsonifiedString,
			'type': 'POST',
		    'contentType': 'application/json',
		    'processData': false,
			'success': function (result) {
				if (result.error == true) {
					swal("Error", result.errorMessage, "error");
					return false;
				}

				requirement.yourBasicRating = value;
                var newBasicUserRatings = {};
                for (var userID in requirement.userBasicRatings) {
                    if (userID != dataManager.currentUserID) {
                        newBasicUserRatings[userID] = requirement.userBasicRatings[userID];
                    }
                }
                requirement.userBasicRatings = newBasicUserRatings;
                if (value != 0) {
                    requirement.userBasicRatings[dataManager.currentUserID] = newYourRatingDataDetailed;
                }
                showBasicScore(requirement, requirement.userBasicRatings, dataManager);

				if (value != 0) {
				    uiEventHandler.basicRequirementEvaluationEvent(event, thisObj, null);
				}
			}
		});
		return false;
	}

    saveRatingEvent(event, thisObj) {
        var requirementID = parseInt($(thisObj).parent().attr("id").split("-")[2]);
        //var requirementID = parseInt($(".or-rating-self-vote").attr("data-requirement-id"));
		var requirement = this.uiManager.dataManager.requirementData.filter(function (req) { return req.id == requirementID; })[0];
		var projectID = this.uiManager.projectID;
		var isPrivateProject = this.uiManager.dataManager.projectData.isPrivateProject;
		var uiEventHandler = this;
		var dataManager = this.dataManager;
		var fields = $(".or-rating-table:last > tbody > tr > td.or-rating-field-cell > div > select.or-rating-field");

		var ratingAttributeIDMap = {};
		for (var i in dataManager.ratingAttributeData) {
			var ratingAttributeInfo = dataManager.ratingAttributeData[i];
			ratingAttributeIDMap[ratingAttributeInfo.id] = ratingAttributeInfo;
		}

		var attributeVotes = [];
		var newYourRatingData = {};
		var newYourRatingDataDetailed = [];
		fields.each(function () {
			var attributeID = $(this).attr("data-rating-attribute-id");
			var value = parseFloat(($(this).val() != "") ? $(this).val() : 0.0);
			attributeVotes.push({ ratingAttributeID: attributeID, ratingValue: value });

			if (value != 0) {
				newYourRatingData[attributeID] = value;
				var ratingInfo = { attributeID: attributeID, value: value };
				ratingInfo["userFirstName"] = dataManager.userData.userFirstName;
				ratingInfo["userLastName"] = dataManager.userData.userLastName;
				ratingInfo["userEmail"] = dataManager.userData.userEmail;
				newYourRatingDataDetailed.push(ratingInfo);
			}
		});

        var anonymousUsername = isPrivateProject ? "" : $(".or-rating-anonymous-username:last").val();
        if ((!isPrivateProject) && (anonymousUsername == "")) {
            alert("Please enter a name!");
            this.rateEvent(event, thisObj);
            return false;
        }
		var url = "/project/" + projectID + "/requirement/vote.json";
		var jsonifiedString = JSON.stringify({
			requirementID: requirementID,
			attributeVotes: attributeVotes,
			anonymousVoting: (!isPrivateProject),
			anonymousStakeholderName: anonymousUsername
		});

		$.ajax(url, {
		    'data': jsonifiedString,
			'type': 'POST',
		    'contentType': 'application/json',
		    'processData': false,
			'success': function (result) {
				if (result.error == true) {
					swal("Error", result.errorMessage, "error");
					return false;
				}

				var oldRating = requirement.yourRating;
				requirement.yourRating = newYourRatingData;
				var ratings = null;
				if (dataManager.currentUserID == 0) {
					var newAnonymousRatings = {};
					for (var userID in requirement.anonymousRatings) {
						if (requirement.anonymousRatings[userID].nameOfAnonymousUser != anonymousUsername) {
							newAnonymousRatings[userID] = requirement.anonymousRatings[userID];
						}
					}
					requirement.anonymousRatings = newAnonymousRatings;
                    if (newYourRatingDataDetailed.length > 0) {
                        requirement.anonymousRatings[anonymousUsername] = newYourRatingDataDetailed;
                    }
					ratings = requirement.anonymousRatings;
				} else {
					var newUserRatings = {};
					for (var userID in requirement.userRatings) {
						if (userID != dataManager.currentUserID) {
							newUserRatings[userID] = requirement.userRatings[userID];
						}
					}
					requirement.userRatings = newUserRatings;
					if (newYourRatingDataDetailed.length > 0) {
					    requirement.userRatings[dataManager.currentUserID] = newYourRatingDataDetailed;
                    }
					ratings = requirement.userRatings;
				}

				var ratingCount = 0;
				for (var userID in ratings) {
					if (ratings[userID].length > 0) {
						++ratingCount;
					}
				}

                showMAUTScore(requirement, ratings, dataManager.ratingAttributeData, dataManager);

				if (Object.keys(newYourRatingData).length > 0) {
				    uiEventHandler.rateEvent(event, thisObj, null);
				}
			}
		});
		return false;
	}

	specifyAssessmentSchemeEvent(event, thisObj) {
		$(".dropdown-button").dropdown("close");
		var projectID = this.uiManager.projectID;
        var uiEventHandler = this;
        var isPrivateProject = this.uiManager.dataManager.projectData.isPrivateProject;
        $(".or-stakeholder-rating-scheme").hide();
        var dataManager = this.dataManager;
        console.log(dataManager.ratingAttributeData);

        $.getJSON("/project/" + projectID + "/requirement/rating/attribute/list.json", function (result) {
			var tbody = $(".or-rating-attribute-table > tbody");
			tbody.children("tr").remove();
			for (var i in result) {
				var ratingAttribute = result[i];
				var ratingAttriuteIcon = $("<i></i>").addClass("material-icons left").text(ratingAttribute.iconName);
				var ratingAttributeValueField = $("<input />").attr("name", "or-rating-attribute-" + ratingAttribute.id).attr("data-rating-attribute-id", ratingAttribute.id).attr("value", ratingAttribute.weight);
				var tr = $("<tr></tr>");
				var ratingAttributeNameColumn = $("<td></td>").addClass("or-rating-attribute-name-cell").append(ratingAttriuteIcon).append(" " + ratingAttribute.name);
				var ratingAttributeDescriptionColumn = $("<td></td>").addClass("or-rating-attribute-description-cell").append(ratingAttribute.description);
				var ratingAttributeValueColumn = $("<td></td>").addClass("or-rating-attribute-value-cell").append(ratingAttributeValueField);
				var deleteRatingAttributeButton = $("<a></a>").addClass("or-delete-rating-attribute-button btn-floating btn-small waves-effect waves-light red lighten-2");
				deleteRatingAttributeButton.attr("data-rating-attribute-id", ratingAttribute.id);
				deleteRatingAttributeButton.append($("<i></i>").addClass("material-icons").text("delete"));

				tr.append(ratingAttributeNameColumn);
				tr.append(ratingAttributeDescriptionColumn);
				tr.append(ratingAttributeValueColumn);
				tr.append($("<td></td>").append(deleteRatingAttributeButton));
				tbody.append(tr);
			}

			if (!isPrivateProject) {
                uiEventHandler.specifyAssessmentSchemeEventCallback();
                return;
            }

            uiEventHandler.specifyAssessmentSchemeEventCallback();
			/*
            $.getJSON("/project/" + projectID + "/requirement/stakeholder/rating/attribute/list.json", function (result) {
                var tbody = $(".or-stakeholder-assessment-rating-attribute-table > tbody");
                tbody.children("tr").remove();
                for (var i in result) {
                    var ratingAttribute = result[i];
                    var ratingAttriuteIcon = $("<i></i>").addClass("material-icons left").text(ratingAttribute.iconName);
                    var ratingAttributeValueField = $("<input />").attr("name", "or-rating-attribute-" + ratingAttribute.id).attr("data-rating-attribute-id", ratingAttribute.id).attr("value", ratingAttribute.weight);
                    var tr = $("<tr></tr>");
                    var ratingAttributeNameColumn = $("<td></td>").addClass("or-rating-attribute-name-cell").append(ratingAttriuteIcon).append(" " + ratingAttribute.name);
                    var ratingAttributeDescriptionColumn = $("<td></td>").addClass("or-rating-attribute-description-cell").append(ratingAttribute.description);
                    var ratingAttributeValueColumn = $("<td></td>").addClass("or-rating-attribute-value-cell").append(ratingAttributeValueField);

                    tr.append(ratingAttributeNameColumn);
                    tr.append(ratingAttributeDescriptionColumn);
                    tr.append(ratingAttributeValueColumn);
                    tbody.append(tr);
                }

                $(".or-stakeholder-rating-scheme").show();
                uiEventHandler.specifyAssessmentSchemeEventCallback();
            });
            */
		});
		return false;
	}

    specifyAssessmentSchemeEventCallback(event, thisObj) {
        var ratingAttributeContainer = $(".or-rating-attribute-container");
        var ratingAttributeContainerContent = ratingAttributeContainer.wrap('<p/>').parent().html();
        ratingAttributeContainer.unwrap();
        var uiEventHandler = this;

        swal({
            html: '<div class="or-share-area">'
            + ratingAttributeContainerContent
            + '</div>',
            showCancelButton: true,
            confirmButtonText: "OK",
            cancelButtonText: "Cancel"
        }).then(function (result) {
            if (("value" in result) && result.value) {
                if (result.value) {
                    uiEventHandler.saveAssessmentSchemeEvent(event, thisObj);
                }
                return true;
            }
        });
        $(".or-rating-attribute-table:last > tbody > tr:first > td.or-rating-attribute-value-cell > input").focus();
        uiEventHandler.bindUIEvents();
    }

	saveAssessmentSchemeEvent(event, thisObj) {
		var projectID = this.uiManager.projectID;
		var dataManager = this.dataManager;
		var fields = $(".or-rating-attribute-table:last > tbody > tr > td.or-rating-attribute-value-cell > input");

		var ratingAttributeIDMap = {};
		for (var i in dataManager.ratingAttributeData) {
			var ratingAttributeInfo = dataManager.ratingAttributeData[i];
			ratingAttributeIDMap[ratingAttributeInfo.id] = ratingAttributeInfo;
		}

		var newRatingAttributeData = [];
		fields.each(function () {
			var attributeID = $(this).attr("data-rating-attribute-id");
			var attributeData = ratingAttributeIDMap[attributeID];
			attributeData.weight = parseFloat($(this).val());
			newRatingAttributeData.push(attributeData);
		});

		$.ajax("/project/" + projectID + "/requirement/rating/attribute/update.json", {
		    'data': JSON.stringify(newRatingAttributeData),
			'type': 'POST',
		    'contentType': 'application/json',
		    'processData': false,
			'success': function (result) {
				if (result.error == true) {
					swal("Error", result.errorMessage, "error");
					return false;
				}
				dataManager.ratingAttributeData = newRatingAttributeData;
			}
		});

        var stakeholderWeightFields = $(".or-stakeholder-assessment-rating-attribute-table:last > tbody > tr > td.or-rating-attribute-value-cell > input");

        var stakeholderRatingAttributeIDMap = {};
        for (var i in dataManager.stakeholderRatingAttributeData) {
            var stakeholderRatingAttributeInfo = dataManager.stakeholderRatingAttributeData[i];
            stakeholderRatingAttributeIDMap[stakeholderRatingAttributeInfo.id] = stakeholderRatingAttributeInfo;
        }

        var newStakeholderRatingAttributeData = [];
        stakeholderWeightFields.each(function () {
            var attributeID = $(this).attr("data-rating-attribute-id");
            var attributeData = stakeholderRatingAttributeIDMap[attributeID];
            attributeData.weight = parseFloat($(this).val());
            newStakeholderRatingAttributeData.push(attributeData);
        });

        if (newStakeholderRatingAttributeData.length == 0) {
            return false;
        }

        $.ajax("/project/" + projectID + "/requirement/stakeholder/rating/attribute/update.json", {
            'data': JSON.stringify(newStakeholderRatingAttributeData),
            'type': 'POST',
            'contentType': 'application/json',
            'processData': false,
            'success': function (result) {
                if (result.error == true) {
                    swal("Error", result.errorMessage, "error");
                    return false;
                }
                dataManager.stakeholderRatingAttributeData = newStakeholderRatingAttributeData;
            }
        });
        return false;
	}

    navigationClickEvent(event, thisObj) {
        $(".or-container").each(function(){
            if ($(this).hasClass("active")) {
                var containerID = $(this).attr("href");
                $(this).removeClass("active");
                //$(containerID).hide("slide", {direction: 'left'});
                $(containerID).hide();
            }
        });
        var containerID = $(thisObj).attr("href");
        $(thisObj).addClass("active");
        $(containerID).fadeIn();
        if (containerID == "#container-dependencies") {
            $(".or-consistency-check-message").hide();
            this.showDependenciesContainerEvent(event, thisObj);
        } else if (containerID == "#container-statistics") {
            var projectID = this.uiManager.projectID;
            $("#iframe-depenency-wheel").attr("src", "/project/" + projectID + "/statistics/graph/dependency");
        }
        this.bindUIEvents();
        return false;
	}

	createAndAppendDependencyRow(dependencyInfo, dependencyList) {
        var dependencyEntityContainer = $("#or-dependency-entity-container > .collapsible-header > .or-dependency-entity");
        dependencyEntityContainer = dependencyEntityContainer.clone();
        dependencyEntityContainer.attr("id", "or-dependency-" + dependencyInfo.type + "-" + dependencyInfo.sourceRequirementID + "-" + dependencyInfo.targetRequirementID);
        var dependencySignMap = {
            IMPLIES: "&ge;",
            REQUIRES: "&gt;",
            EXCLUDES: "x",
            INCOMPATIBLE: "&ne;"
        };
        dependencyEntityContainer.children(".or-dependency-left")
            .append($("<div></div>").addClass("or-dependency-title").text(dependencyInfo.sourceRequirementTitle))
            .append($("<div></div>").addClass("or-dependency-description").text(truncate(dependencyInfo.sourceRequirementDescription.replace(/<\/?[^>]+(>|$)/g, ""), 140)));
        dependencyEntityContainer.children(".or-dependency-middle").children(".or-dependency-type")
            .html(dependencySignMap[dependencyInfo.type]);
        dependencyEntityContainer.children(".or-dependency-right")
            .append($("<div></div>").addClass("or-dependency-title").text(dependencyInfo.targetRequirementTitle))
            .append($("<div></div>").addClass("or-dependency-description").text(truncate(dependencyInfo.targetRequirementDescription.replace(/<\/?[^>]+(>|$)/g, ""), 140)));
        dependencyEntityContainer.children(".or-dependency-delete").children(".or-delete-dependency-button")
            .attr("data-dependency-source-id", dependencyInfo.sourceRequirementID)
            .attr("data-dependency-target-id", dependencyInfo.targetRequirementID)
            .attr("data-dependency-type", dependencyInfo.type);
        var dependencyEntityContainerContent = dependencyEntityContainer.wrap('<p/>').parent().html();
        dependencyEntityContainer.unwrap();
        dependencyList.append(dependencyEntityContainerContent);
    }

	showDependenciesContainerEvent(event, thisObj) {
        var projectID = this.uiManager.projectID;
        var dataManager = this.uiManager.dataManager;
        var requirementData = this.uiManager.dataManager.requirementData;
        $("#or-no-dependency-found-placeholder").hide();

        var select = $("<select></select>").addClass("or-dependency-field");
        select.append($("<option></option>").val("").attr("disabled", "disabled").attr("selected", "selected").text("Select requirement"));
        for (var i in requirementData) {
            var requirementInfo = requirementData[i];
            select.append($("<option></option>").val(requirementInfo.id).text(requirementInfo.title + " (#" + requirementInfo.id + ")"));
        }

        // TODO: make optgroup for each release
        /*
        <optgroup label="Unassigned Requirements">
            <option value="1">Requirement 1</option>
            ...
        </optgroup>
        */

        var left = $(".or-create-dependency-row > .or-dependency-left");
        var right = $(".or-create-dependency-row > .or-dependency-right");
        left.children().remove();
        right.children().remove();
        var requirementXButton = select.clone();
        var requirementYButton = select.clone();
        requirementXButton.attr("id", "or-dependency-requirement-x-field");
        requirementYButton.attr("id", "or-dependency-requirement-y-field");
        left.append(requirementXButton);
        right.append(requirementYButton);
        $("select.or-dependency-field").material_select();
        $("span.caret").text("");

        var uiEventHandler = this;
        $.getJSON("/project/" + projectID + "/dependency/list.json", function (result) {
            dataManager.dependencyData = result;
            var dependencyList = $(".or-dependency-list");
            dependencyList.children().remove();

            for (var i in result) {
                this.createAndAppendDependencyRow(result[i], dependencyList);
            }

            if (result.length == 0) {
                $("#or-no-dependency-found-placeholder").show();
            }
            uiEventHandler.bindUIEvents();
        }.bind(this));
    }

    createDependencyClickEvent(event, thisObj) {
        var projectID = this.uiManager.projectID;
        var dataManager = this.uiManager.dataManager;
	    var rxField = $("#or-dependency-requirement-x-field");
	    var ryField = $("#or-dependency-requirement-y-field");
	    var IDOfRequirementX = rxField.parent("div.or-dependency-field").children("input.select-dropdown").val();
	    var IDOfRequirementY = ryField.parent("div.or-dependency-field").children("input.select-dropdown").val();
	    var type = $("#or-dependency-type-icon").attr("data-type");
	    IDOfRequirementX = IDOfRequirementX.split("#")[1].split(")")[0];
	    IDOfRequirementY = IDOfRequirementY.split("#")[1].split(")")[0];

        var uiEventHandler = this;
        dataManager.createDependency(projectID, IDOfRequirementX, IDOfRequirementY, type, function (result) {
            if (result.error == true) {
                swal("Error", result.errorMessage, "error");
                return false;
            }

            var dependencyList = $(".or-dependency-list");
            uiEventHandler.createAndAppendDependencyRow(result.dependencyInfo, dependencyList);
            $("#or-no-dependency-found-placeholder").hide();
            uiEventHandler.bindUIEvents();
        });
        return false;
    }

    deleteDependencyClickEvent(event, thisObj) {
        var projectID = this.uiManager.projectID;
        var IDOfRequirementX = $(thisObj).attr("data-dependency-source-id");
        var IDOfRequirementY = $(thisObj).attr("data-dependency-target-id");
        var type = $(thisObj).attr("data-dependency-type");

        var jsonifiedString = JSON.stringify({
            sourceRequirementID: IDOfRequirementX,
            targetRequirementID: IDOfRequirementY,
            type: type
        });

        var uiEventHandler = this;
        var dependencyEntityRow = $(thisObj).parent(".or-dependency-delete").parent(".or-dependency-entity");
        $.ajax("/project/" + projectID + "/dependency/delete.json", {
            'data': jsonifiedString,
            'type': 'POST',
            'contentType': 'application/json',
            'processData': false,
            'success': function (result) {
                if (result.error == true) {
                    swal("Error", result.errorMessage, "error");
                    return false;
                }

                dependencyEntityRow.fadeOut();
                uiEventHandler.bindUIEvents();
            }
        });
        return false;
    }

    changeDependencyTypeClickEvent(event, thisObj) {
        $(".dropdown-button").dropdown("close");

	    var dependencyTypeID = $(thisObj).attr("id");
	    if (dependencyTypeID == "or-dependency-type-menu-implies") {
            $("#or-dependency-type-icon")
                .attr("data-type", "IMPLIES")
                .html("&ge;");
        } else if (dependencyTypeID == "or-dependency-type-menu-requires") {
            $("#or-dependency-type-icon")
				.attr("data-type", "REQUIRES")
				.html("&gt;");
        } else if (dependencyTypeID == "or-dependency-type-menu-excludes") {
            $("#or-dependency-type-icon")
                .attr("data-type", "EXCLUDES")
                .html("x");
        } else if (dependencyTypeID == "or-dependency-type-menu-incompatible") {
            $("#or-dependency-type-icon")
                .attr("data-type", "INCOMPATIBLE")
                .html("&ne;");
        }
	    return false;
    }

    importRecommendedDependenciesClickEvent(event, thisObj) {
        var projectID = this.uiManager.projectID;
        var projectKey = this.uiManager.projectKey;
        var uiEventHandler = this;
        var dataManager = this.uiManager.dataManager;
        var requirementData = this.uiManager.dataManager.requirementData;
        var requirementsMap = {};
        for (var idx in requirementData) {
            var requirement = requirementData[idx];
            requirementsMap[requirement.id] = requirement;
        }

        $.getJSON("/project/" + projectKey + "/dependency/recommend", function(data) {
            var listContainer = $(".or-recommended-dependency-list-container");
            listContainer.children().not(".or-no-dependency-placeholder-row").remove();

            $(".or-no-dependency-placeholder-row").show();
            var furtherDependenciesFound = false;

            for (var idx in data) {
                var rx = data[idx].id;
                for (var innerIdx in data[idx].predictions) {
                    var ry = data[idx].predictions[innerIdx];
                    var dependencyAlreadyExists = false;
                    for (var i in dataManager.dependencyData) {
                        var existingDependency = dataManager.dependencyData[i];
                        var existingRx = existingDependency.sourceRequirementID;
                        var existingRy = existingDependency.targetRequirementID;
                        if (((rx == existingRx) && (ry == existingRy)) || ((rx == existingRy) && (ry == existingRx))) {
                            dependencyAlreadyExists = true;
                            break;
                        }
                    }

                    if (dependencyAlreadyExists) {
                        continue;
                    }

                    var row = $("<div></div>").addClass("or-select-dependency-row or-dependency-row row");
                    var checkBoxColumn = $("<div></div>").addClass("or-import-dependency-checkbox col-xs-1 col-md-1");
                    var checkBox = $("<input />")
                        .attr("type", "checkbox")
                        .addClass("or-import-dependency-checkbox")
                        .attr("data-rx", rx)
                        .attr("data-ry", ry)
                        .attr("data-type", "REQUIRES");
                    checkBoxColumn.append(checkBox);
                    checkBoxColumn.append("<label></label>");
                    row.append(checkBoxColumn);

                    var rxColumn = $("<div></div>").addClass("col-xs-4 col-md-4");
                    rxColumn.html(requirementsMap[rx].title + " (#" + rx + ")");
                    row.append(rxColumn);

                    var typeColumn = $("<div></div>").addClass("col-xs-2 col-md-2");
                    var swapIcon = $("<div></div>")
                        .addClass("or-dependency-type or-dependency-swap-direction dp48")
                        .html("&gt;");
                    typeColumn.append(swapIcon);
                    row.append(typeColumn);

                    var ryColumn = $("<div></div>").addClass("col-xs-5 col-md-5");
                    ryColumn.html(requirementsMap[ry].title + " (#" + ry + ")");
                    row.append(ryColumn);

                    listContainer.append(row);
                    $(".or-no-dependency-placeholder-row").hide();
                    furtherDependenciesFound = true;
                }
            }

            var importContainer = $(".or-import-dependency-recommendations-container");
            var importContainerContent = importContainer.wrap('<p/>').parent().html();
            importContainer.unwrap();

            swal({
                html: importContainerContent,
                showCancelButton: true,
                cancelButtonText: "Cancel",
                showConfirmButton: furtherDependenciesFound,
                confirmButtonClass: "btn-danger",
                confirmButtonText: "Import"
            }).then(function (result) {
                if (("value" in result) && result.value) {
                    $(".or-import-dependency-checkbox").each(function (index, checkBoxSelector) {
                        if ($(checkBoxSelector).is(':checked')) {
                            var rx = $(checkBoxSelector).attr("data-rx");
                            var ry = $(checkBoxSelector).attr("data-ry");
                            var type = $(checkBoxSelector).attr("data-type");

                            dataManager.createDependency(projectID, rx, ry, type, function (result) {
                                if (result.error == true) {
                                    swal("Error", result.errorMessage, "error");
                                    return false;
                                }

                                var dependencyList = $(".or-dependency-list");
                                uiEventHandler.createAndAppendDependencyRow(result.dependencyInfo, dependencyList);
                                $("#or-no-dependency-found-placeholder").hide();
                            });
                        }
                    }.bind(this));
                    uiEventHandler.bindUIEvents();
                    return true;
                }
            }.bind(this));
            $(".swal2-popup").css("width", "600px");
            $(".or-select-dependency-row").click(function () {
                var checkBox = $(this).children(".or-import-dependency-checkbox").children("input");
                if (checkBox.is(':checked')) {
                    $(this).removeClass("or-dependency-row-selected");
                    checkBox.prop('checked', false);
                } else {
                    $(this).addClass("or-dependency-row-selected");
                    checkBox.prop('checked', true);
                }
            });
        }.bind(this)).error(function() {
            swal("Error", "The service is currently not available! Please try again later or contact the administrator.", "error");
        });
        return false;
	}

    importRequirementsFromTwitterClickEvent(event, thisObj) {
        $(".dropdown-button").dropdown("close");
	    var projectKey = this.uiManager.projectKey;
	    var projectSettings = this.uiManager.dataManager.projectData.projectSettings;
        var twitterChannel = (projectSettings.twitterChannel != "") ? projectSettings.twitterChannel.replace("#", "").replace("@", "") : "FitbitSupport";
        var categoryClasses = ["inquiry", "problem_report", "irrelevant"];

        for (var i in categoryClasses) {
            var url = "https://api.openreq.eu/ri-storage-twitter/account_name/" + twitterChannel
                + "/class/" + categoryClasses[i];
            var htmlClass = ".container-tweets-" + categoryClasses[i] + " > .or-tweets-table > tbody";
            var data = [];
            $.ajax(url, {
                "type": "GET",
                "contentType": "application/json",
                "processData": false,
                "async": false,
                "success": function (d) { data = d }
            });

            $(htmlClass).children().remove();
            var prefix = categoryClasses[i].charAt(0).toUpperCase();

            if (categoryClasses[i] == "inquiry") {
                prefix = "INQ";
            } else if (categoryClasses[i] == "problem_report") {
                prefix = "PRE";
            } else if (categoryClasses[i] == "irrelevant") {
                prefix = "IRR";
            }

            for (var i in data) {
                var requirementCandidateText = data[i].text.replace("@" + twitterChannel, "");
                var tweetID = prefix + "-" + (parseInt(i) + 1);
                var addRequirementCandidateButton = $("<a></a>")
                    .addClass("or-import-requirement-button waves-effect waves-light btn blue darken-3")
                    .html("<i class=\"material-icons left\">add</i> Add")
                    .attr("data-tweet-id", tweetID);
                var importIDs = {};
                $(".or-requirement-title").each(function () {
                    importIDs[$(this).attr("data-import-id")] = true;
                });
                if (tweetID in importIDs) {
                    addRequirementCandidateButton.attr("disabled", "disabled");
                }
                var tr = $("<tr></tr>")
                    .addClass("or-requirement-candidate")
                    .append($("<td></td>").html("<b>" + tweetID + "</b>"))
                    .append($("<td></td>").addClass("or-tweet-message").text(requirementCandidateText))
                    .append($("<td></td>").append(addRequirementCandidateButton));
                $(htmlClass).append(tr);
            }
        }

        var tweetsContainer = $(".or-tweets-container");
        var tweetsContainerContent = tweetsContainer.wrap('<p/>').parent().html();
        tweetsContainer.unwrap();

        swal({
            html: tweetsContainerContent,
            showCancelButton: false,
            confirmButtonText: "Close"
        });
        $(".swal2-popup").css("width", "900px");
        $(".or-tweets-view").hide();
        $(".container-tweets-inquiry").show();
        this.bindUIEvents();
        return false;
    }

    moveableRequirementsEvent(event, thisObj) {
		$(".dropdown-button").dropdown("close");
		if (!this.isMoveable) {
			$(".or-requirement-move").fadeIn("slow");
			$(".or-requirement").addClass("or-moveable");
			this.isMoveable = true;
			this.bindUIEvents();
			$(".or-moveable").draggable("enable");

			// disable deleting
			this.isDeleteable = true;
			this.deleteableRequirementsEvent(event, thisObj);
		} else {
			this.disableMovingRequirementsEvent(event, thisObj);
		}
		return false;
	}

	deleteableRequirementsEvent(event, thisObj) {
		$(".dropdown-button").dropdown("close");
		if (!this.isDeleteable) {
			$(".or-requirement-delete").fadeIn("slow");
			this.isDeleteable = true;
			this.bindUIEvents();

			// disable moving
			this.isMoveable = true;
			this.moveableRequirementsEvent(event, thisObj);
		} else {
			this.disableDeletingRequirementsEvent(event, thisObj);
		}
		return false;
	}

    projectSettingsEvent(event, thisObj) {
        $(".dropdown-button").dropdown("close");
	    var dataManager = this.uiManager.dataManager;
	    var projectID = this.uiManager.projectID;
	    var projectSettings = dataManager.projectData.projectSettings;

	    if (projectSettings.showDependencies) {
            $(".or-settings-show-dependencies-tab").attr("checked", "checked");
        } else {
            $(".or-settings-show-dependencies-tab").removeAttr("checked");
        }

	    if (projectSettings.showStatistics) {
            $(".or-settings-show-statistics-tab").attr("checked", "checked");
        } else {
            $(".or-settings-show-statistics-tab").removeAttr("checked");
        }

	    if (projectSettings.showSocialPopularityIndicator) {
            $(".or-settings-show-social-popularity-indicator").attr("checked", "checked");
        } else {
            $(".or-settings-show-social-popularity-indicator").removeAttr("checked");
        }

	    if (projectSettings.showStakeholderAssignment) {
            $(".or-settings-show-stakeholder-assignment").attr("checked", "checked");
        } else {
            $(".or-settings-show-stakeholder-assignment").removeAttr("checked");
        }

	    if (projectSettings.showAmbiguityAnalysis) {
            $(".or-settings-show-ambiguity-analysis").attr("checked", "checked");
        } else {
            $(".or-settings-show-ambiguity-analysis").removeAttr("checked");
        }

        $(".or-settings-twitter-channel").attr("value", projectSettings.twitterChannel);

        var settingsContainer = $(".or-settings-container");
        var settingsContainerContent = settingsContainer.wrap('<p/>').parent().html();
        settingsContainer.unwrap();

        swal({
            html: settingsContainerContent,
            showCancelButton: true,
            cancelButtonText: "Cancel",
            confirmButtonClass: "btn-danger",
            confirmButtonText: "Save"
        }).then(function (result) {
            if (("value" in result) && result.value) {
                projectSettings.showDependencies = $(".or-settings-show-dependencies-tab:last").prop("checked");
                projectSettings.showStatistics = $(".or-settings-show-statistics-tab:last").prop("checked");
                projectSettings.showSocialPopularityIndicator = $(".or-settings-show-social-popularity-indicator:last").prop("checked");
                projectSettings.showStakeholderAssignment = $(".or-settings-show-stakeholder-assignment:last").prop("checked");
                projectSettings.showAmbiguityAnalysis = $(".or-settings-show-ambiguity-analysis:last").prop("checked");
                projectSettings.evaluationMode = $(".or-evaluation-mode-field:last").val();
                projectSettings.twitterChannel = $(".or-settings-twitter-channel:last").val();

                var jsonifiedString = JSON.stringify({
                    "showDependencies": projectSettings.showDependencies,
                    "showStatistics": projectSettings.showStatistics,
                    "showSocialPopularityIndicator": projectSettings.showSocialPopularityIndicator,
                    "showStakeholderAssignment": projectSettings.showStakeholderAssignment,
                    "showAmbiguityAnalysis": projectSettings.showAmbiguityAnalysis,
                    "evaluationMode": projectSettings.evaluationMode,
                    "twitterChannel": projectSettings.twitterChannel
                });

                $.ajax("/project/" + projectID + "/settings/update.json", {
                    "data": jsonifiedString,
                    "type": "POST",
                    "contentType": "application/json",
                    "processData": false,
                    "success": function (data) {
                        if (data.error) {
                            swal("Error", data.errorMessage, "error");
                            return false;
                        }

                        swal({title: "Saved!", text: "The settings have been saved.", type: "success", timer: 800, showCancelButton: false, showConfirmButton: false});
                        this.uiManager.showVisiblePanelsAndViews();
                    }.bind(this)
                });

                var twitterChannel = projectSettings.twitterChannel.replace("@", "").replace("#", "");
                var url = "https://api.openreq.eu/ri-orchestration-twitter/hitec/orchestration/twitter/observe" +
                    "/tweet/account/" + twitterChannel + "/interval/2h/lang/en";
                $.ajax(url, {
                    "data": jsonifiedString,
                    "type": "POST",
                    "contentType": "application/json",
                    "processData": false,
                    "success": function (data) {
                        if (!data.status) {
                            swal("Error", data.message, "error");
                            return false;
                        }

                        swal({
                            title: "Twitter Service installed!",
                            text: "The Twitter service has been installed. It will run every 2 hours.",
                            type: "success",
                            timer: 2000,
                            showCancelButton: false,
                            showConfirmButton: false
                        });
                        this.uiManager.showVisiblePanelsAndViews();
                    }.bind(this)
                });
                return true;
            }
        }.bind(this));
        $("select.or-evaluation-mode-field").val(projectSettings.evaluationMode);
        if (projectSettings.readOnly) {
            $("select.or-evaluation-mode-field")
                .prop("disabled", true)
                .attr("disabled", "disabled");
        }
        $("select.or-evaluation-mode-field").material_select();
        $("span.caret").text("");
        return false;
	}

	disableMovingRequirementsEvent(event, thisObj) {
		$(".or-requirement-move").fadeOut("slow", "swing");
		$(".or-moveable").draggable("disable");
		$(".or-requirement").removeClass("or-moveable"); //.removeClass("ui-draggable").removeClass("ui-draggable-handle");
		this.isMoveable = false;
	}

	disableDeletingRequirementsEvent(event, thisObj) {
		$(".or-requirement-delete").fadeOut("slow", "swing");
		this.isDeleteable = false;
	}

	updateVisibilityEvent(event, thisObj) {
        thisObj.blur();
        $(".ui-tooltip").hide();
		var buttonID = $(thisObj).attr("id");
		var isPrivateProject = (buttonID == "or-visibility-button-private");
		swal({
            title: '<div style="padding-bottom:20px;"><i class="material-icons" style="font-size:140px;">' + (isPrivateProject ? "visibility" : "visibility_off") + '</i></div><div>This project is ' + (isPrivateProject ? 'private' : 'public') + '.</div>',
            html: 'Do you want to make it ' + (isPrivateProject ? 'public' : 'private') + ' now?',
            //type: "warning",
            showCancelButton: true,
            cancelButtonText: "No",
            //confirmButtonClass: "btn-danger",
            confirmButtonText: "Yes, make it " + (isPrivateProject ? 'public' : 'private') + "!"
        }).then(function (result) {
            if (("value" in result) && result.value) {
            		window.location = "/project/p/" + this.uiManager.projectKey + "/visibility/set/" + (isPrivateProject ? 'public' : 'private');
            		return true;
            }
        }.bind(this));
	}

	datePickerEvent(event, thisObj) {
		var uiEventHelper = this;
		this.datePickerOptions = {
		    selectMonths: true,
		    selectYears: 5,
		    format: 'yyyy-mm-dd',
		    min: true,
		    today: 'Today',
		    clear: false,
		    close: false,
		    closeOnSelect: true,
		    onRender: function() {
              $(".or-picker-date-label").remove();
              //$(".picker__date-display").prepend($("<div></div>").addClass("or-picker-date-label").text(isStartDate ? "Start date" : "End date"));
		    }, /*
		    onOpen: function() {
		      console.log('Opened up date');
		    }, */
		    onClose: bindEach(this, "datePickerCloseEvent", null),
		    onSet: function(context) {
              var dateFieldID = this.$node.context.id;
              if (context.select === undefined) {
                var initialValue = $("#" + dateFieldID).attr("data-initial-value");
                $("#" + dateFieldID).val(initialValue);
              }
              uiEventHelper.unsavedChangesEvent();
		    }
		};
		$(".or-form-release-end-date-field").pickadate(this.datePickerOptions);
		$(thisObj).parent('div').children('.or-form-release-end-date-field').click();
		return false;
	}

	datePickerCloseEvent(thisObj) {
        var dateFieldID = thisObj.$node.context.id;
        var dateBadgeText = $("#" + dateFieldID).parent().children(".or-date-badge").children(".or-date-badge-text");
        var dateTimeParts = $("#" + dateFieldID).val().split("-");
        var date = new Date(dateTimeParts[0], (Number(dateTimeParts[1]) - 1), dateTimeParts[2], 0, 0, 0, 0);
		dateBadgeText.text(formatDateText(date));
	}

	editReleaseDescriptionEvent(event, thisObj) {
		var link = $(thisObj);
		var descriptionField = link.parent("div").parent("div.row").children(".or-form-edit-release-description-col").children(".or-form-field");
		descriptionField.removeClass("or-form-field-disabled");
		descriptionField.prop("disabled", false).focus();
		link.hide();
		return false;
	}

	focusOutEditReleaseDescriptionEvent(event, thisObj) {
		var descriptionField = $(thisObj);
		var link = descriptionField.parent(".or-form-edit-release-description-col").parent("div.row").children(".or-form-edit-release-description-link-col").children();
		descriptionField.addClass("or-form-field-disabled");
		descriptionField.prop("disabled", true);
		link.show();
		return false;
	}

	unsavedChangesEvent() {
		this.uiManager.showUnsavedChangesNotificationIfNecessary();
		return false;
	}

	ignoreClickHandler() {
		$(".or-ignore-click").unbind("click");
		$(".or-ignore-click").click(function (event) { return false; });
	}

	addReleaseClickEvent(event, thisObj) {
		this.disableMovingRequirementsEvent(event, thisObj);
		this.disableDeletingRequirementsEvent(event, thisObj);
		this.dataManager.collectChanges(false);
		var releaseEndDateTimestamps = this.dataManager.newReleases.map(r => r.endDateTimestamp);
		releaseEndDateTimestamps = releaseEndDateTimestamps.concat(this.dataManager.releaseData.map(r => r.endDateTimestamp));
		releaseEndDateTimestamps = releaseEndDateTimestamps.concat(this.dataManager.ignoredNewReleases.map(r => r.endDateTimestamp));
		var newReleaseStartDateTimestamp = (releaseEndDateTimestamps.length == 0) ? (new Date()).getTime() : Math.max(...releaseEndDateTimestamps);
		var endDate = new Date(newReleaseStartDateTimestamp + (90*24*60*60*1000));
		this.uiManager.addRelease(0, "", "", 1000, endDate.getTime(),
            formatDateText(endDate), true);
		this.bindUIEvents();
		return false;
	}

	addRequirementClickEvent(event, thisObj) {
		this.disableMovingRequirementsEvent(event, thisObj);
		this.disableDeletingRequirementsEvent(event, thisObj);
		var tableSelector = $(thisObj).parent("div.or-add-button-container").parent("div").parent("div.collapsible-body").children("div").children("table.or-project-requirement-table");
		this.uiManager.addRequirementFormFields(tableSelector, null, this.dataManager.ratingAttributeData, true, true, true);
		this.bindUIEvents();
		return false;
	}

	addUnassignedRequirementClickEvent(event, thisObj) {
		this.disableMovingRequirementsEvent(event, thisObj);
		this.disableDeletingRequirementsEvent(event, thisObj);
		var tableSelector = $("#or-project-unassigned-requirements");
		this.uiManager.addRequirementFormFields(tableSelector, null, this.dataManager.ratingAttributeData, true, true, true);
		this.bindUIEvents();
		return false;
	}

	deleteRequirementEvent(event, thisObj) {
		var tr = $(thisObj).parent("td").parent("tr");
		var tbody = tr.parent("tbody");
		if (tbody.children("tr").length == 1) {
			this.uiManager.addRequirementFormFields(tr.parent("tbody").parent("table"), null, this.dataManager.ratingAttributeData, false, true, true);
		}
		tr.remove();
		this.bindUIEvents();
		this.unsavedChangesEvent();
		return false;
	}

	deleteReleaseEvent(event, thisObj) {
		this.disableMovingRequirementsEvent(event, thisObj);
		this.disableDeletingRequirementsEvent(event, thisObj);
		$(thisObj).blur();
        $(".ui-tooltip").hide();
		var uiEventHandler = this;
		swal({
            title: 'Are you sure?',
            text: 'You will not be able to recover the release!',
            type: "warning",
            showCancelButton: true,
            confirmButtonClass: "btn-danger",
            confirmButtonText: "Yes, delete it!"
        }).then(function (result) {
            if (("value" in result) && result.value) {
  			  swal({title: "Deleted!", text: "The release has been successfully deleted.", type: "success", timer: 800, showCancelButton: false, showConfirmButton: false});
			  $(thisObj).parent("div").parent(".or-release-title-row").parent(".collapsible-header").parent(".or-release").remove();
              if ($("ul.or-release-list > li").length == 1) {
                $("#or-no-release-found-placeholder").show();
              }
              uiEventHandler.bindUIEvents();
              uiEventHandler.unsavedChangesEvent();
            }
        });
		/*
		$(thisObj).parent(".collapsible-header").parent(".or-release").slideUp("slow", function() {
			$(this).remove();
			if ($("ul.or-release-list > li").length == 1) {
				$("#or-no-release-found-placeholder").show();
			}
			uiEventHandler.bindUIEvents();
			uiEventHandler.unsavedChangesEvent();
		});
		*/
		return false;
	}

	expandReleaseContainer(liSelector) {
		liSelector.addClass("active");
		liSelector.children(".collapsible-body").show();
		liSelector.children(".collapsible-header").addClass("active");
	}

	collapseReleaseContainer(liSelector) {
		liSelector.removeClass("active");
		liSelector.children(".collapsible-body").hide();
		liSelector.children(".collapsible-header").removeClass("active");
	}
}

class CookieHandler {

	constructor(cookieName, expireDays) {
		this.cookieName = cookieName;
		this.expireDays = expireDays;
	}

	setValue(value) {
		var d = new Date();
		d.setTime(d.getTime() + (this.expireDays*24*60*60*1000));
		var expires = "expires="+ d.toUTCString();
		document.cookie = this.cookieName + "=" + value + ";" + expires + ";path=/";
	}

	getValue() {
		var name = this.cookieName + "=";
		var decodedCookie = decodeURIComponent(document.cookie);
		var ca = decodedCookie.split(';');
		for(var i = 0; i <ca.length; i++) {
			var c = ca[i];
			while (c.charAt(0) == ' ') {
				c = c.substring(1);
			}
			if (c.indexOf(name) == 0) {
				return c.substring(name.length, c.length);
			}
		}
		return "";
	}

}
