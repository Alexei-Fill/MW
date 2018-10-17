function vote() {
    var req = getXmlHttp();
    var butt = document.getElementById('vote_status');
    var butt2 = document.getElementById('vote_count');
    var idElem = document.getElementById('movie_id');
    var col = 0;
    req.onreadystatechange = function() {
        if (req.readyState == 4) {
            if(req.status == 200) {
            col = butt2.innerHTML;
                if(butt.value == "like"){
                 butt.src = "/css/img/notLike.png";
                 butt.value = "notLike";
                 butt2.innerHTML = col - 1;
                } else {
               butt.src ="/css/img/like.png";
               butt.value = "like";
               butt2.innerHTML = col - 1 + 2;
                }
            }
       }
    }
    req.open('GET', '/like?movieId='+idElem.innerHTML, true);
    req.send(null);

}

function getXmlHttp(){
  var xmlhttp;
  try {
    xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
  } catch (e) {
    try {
      xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    } catch (E) {
      xmlhttp = false;
    }
  }
  if (!xmlhttp && typeof XMLHttpRequest!='undefined') {
    xmlhttp = new XMLHttpRequest();
  }
  return xmlhttp;
}
