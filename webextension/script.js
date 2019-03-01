function byTagName(tagName) {
    return document.getElementsByTagName(tagName)[0];
}

byTagName("button").onclick = function() {
    var text = byTagName("input").value;
    byTagName("span").innerHTML = text

    try {
        bookmarkPageInFolder(toolbarBookmarkId, "Ulubione", text)
    }
    catch(err) {
        alert(err.message)
    }
}


