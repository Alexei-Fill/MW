function putGrade() {
    var req = getXmlHttp()
    var rad = document.getElementById('grade_but')
    var grade = document.querySelector('input[name = "grade_but"]:checked').value;
    var idElem = document.getElementById('movie_id')
    req.onreadystatechange = function() {
        if (req.readyState == 4) {
            if(req.status == 200) {

            }
       }
    }
    req.open('GET', '/putAGrade?movieId='+idElem.innerHTML+'&grade='+ grade , true);
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
