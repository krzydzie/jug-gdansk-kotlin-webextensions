const toolbarBookmarkId = "toolbar_____"
const bookmarks = browser.bookmarks;

function bookmarkPageInFolder(parentId, folderName, page) {
    bookmarks.getSubTree(parentId).then(function (tree) {
        var folderNode = findNodeByTitle(tree[0], folderName)

        if (folderNode == null) {
            bookmarks.create({
                parentId: parentId,
                title: folderName
            }).then(function (createdFolder) {
                createBookmark(createdFolder.id, page)
            }, onRejected)
        } else {
            createBookmark(folderNode.id, page)
        }

    }, onRejected)
}

function findNodeByTitle(treeNode, folderName) {
    for(let i in treeNode.children) {
        if (treeNode.children[i].title == folderName) {
            return treeNode.children[i]
        }
    }

    return null
}

function createBookmark(parentId, page) {
    bookmarks.create({
        parentId: parentId,
        title: page,
        url: asUrl(page)
    }).catch(onRejected)
}

function asUrl(page) {
    var url = page

    if (url.indexOf(".") == -1) {
        url += ".pl"
    }

    if (url.indexOf("http") != 0) {
        url = "http://" + url
    }

    return url
}
function onRejected(error) {
    console.log("rejected call: " + error);
}



