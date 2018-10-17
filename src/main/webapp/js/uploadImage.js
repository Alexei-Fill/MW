function previewFile() {
  var preview = document.querySelector('img');
  var file    = document.querySelector('input[type=file]').files[0];
  var reader  = new FileReader();

  reader.onloadend = function () {
    preview.src = reader.result;
  }
  if (file) {
    reader.readAsDataURL(file);
  } else {
    preview.src = "";
  }
  document.getElementById('uploadImageForm').submit();
}

function getImageURL()
{
var imgURL = window.imageURL;
var respondImgURL = window.hiddenframe.document.getElementById('imageURL').value;
if (respondImgURL != ""){
imgURL.value = respondImgURL;
}

}
