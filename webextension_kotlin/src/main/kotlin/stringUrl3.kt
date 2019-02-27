fun String.asUrl() = run {
    if(this.contains(".")) this else "$this.pl"
}. run {
    if(this.indexOf("http") == 0) this else "http://$this"
}