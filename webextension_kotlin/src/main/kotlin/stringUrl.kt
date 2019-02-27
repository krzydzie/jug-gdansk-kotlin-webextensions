fun stringAsUrl(page: String): String {
    var url = if(page.contains(".")) page else "$page.pl"
    url = if(url.indexOf("http") == 0) url else "http://$url"

    return url
}