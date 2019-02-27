function byTagName(tagName) {
    return document.getElementsByTagName(tagName)[0];
}

byTagName("button").onclick = function() {
    var text = byTagName("input").value;
    byTagName("span").innerHTML = text
    bookmarkPageInFolder(toolbarBookmarkId, "Ulubione", text)
}


