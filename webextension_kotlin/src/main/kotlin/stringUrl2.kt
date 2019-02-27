fun stringAsUrl2(page: String) = run {
    if(page.contains(".")) page else "$page.pl"
}. run {
    if(page.indexOf("http") == 0) page else "http://$page"
}