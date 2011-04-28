/**
 * 
 */
function appendYouTubePlayer(var id, var url) {}
    var params = { allowScriptAccess: "always" };
    var atts = { id: "myytplayer" };
    swfobject.embedSWF(url + "?enablejsapi=1&playerapiid=ytplayer", 
                       id, "425", "356", "8", null, null, params, atts);
}
