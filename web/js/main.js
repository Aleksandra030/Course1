/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var rootURL = "http://localhost:8080/MyFirstREST/webresources/service/";
var page = 0;

//function getData() {
//
//    //   search(1);
//}
function search(jsObject) {
    

    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        //promeniii
        url: rootURL + 'ana',
        dataType: "json",
        data: formToJSON(jsObject),
        success: showProjects,
        error: function(jqXHR, textStatus, errorThrown) {
            
            alert('Greska: ' + errorThrown + "\r\n Nije uspelo dovlačenje podataka sa servera. Proverite da li je server pokrenut.");
        }
    });
    
}

function formToJSON(jsObject) {
    
    var a = JSON.stringify(jsObject);

//    var inLanguage = $('#inLanguage').val();
//    if (inLanguage == '')
//        inLanguage = null;
//    var publishers = $('#publishers').val();
//    if (publishers == '')
//        publishers = null;
//    var typicalAgeRange = $('#typicalAgeRange').val();
//    if (typicalAgeRange == '')
//        typicalAgeRange = null;
//
//    var name = $('#name').val();
//    if (name == '')
//        name = null;
//
//
//    var a = JSON.stringify({
//        "name": name,
//        "inLanguage": inLanguage,
//        "publishers": publishers,
//        "typicalAgeRange": typicalAgeRange,
//       
//
//    });
    console.log(a);
    return a;
}

function showProjects(data) {


    var list = data == null ? [] : (data instanceof Array ? data : [data]);

    //$('#selectLic option').remove();    
    var coursesDiv = "";

    if (list.length == 0) {
        if (page > 1)
            search(page - 1);
        else
            $('#searchResultDiv').html("<span class=\"HeadingText\">No matches!</span>")
        return;
    }
    var kurs = $("select[name=izborKursa]").val();
    var klasa = '.'+kurs;
    $.each(list, function(index, c) {
        
        var j=0;
        var lang = "";
        if(c.inLanguage){
            lang = c.inLanguage;
        }
        $('#searchResultDiv').append("<div class=\"coursesDiv\">\n\
<table class=\"projectTable\"><tr><td width=\"60%\" align=left><h1>"+c.name+"</h1></td><td>\n\
TypicalAgeRange :<h3>"+c.typicalAgeRange+"</h3></td><td>Language: <h3>"+lang+"</h3></td></tr>\n\
<tr><td colspan=\"2\">Link: <h3>"+c.URI+"</h3></td></tr>\n\
<tr ><td colspan=\"2\"><h3 >Description:</h3>"+c.description+"</td></tr>\n\n\
<tr class="+j+">");
if(c.authors){
    $.each(c.authors, function(i,autor){
        $("."+j).append("<tr ><td colspan=\"2\"><h3>Author:</h3></td><td colspan=\"2\">"+autor.name+"</td></tr>\n");
        
    });
}
if(c.publisher){
    $.each(c.publisher, function(i,pub){
$("."+j).append("<tr ><td colspan=\"2\"><h3>Publisher:</h3></td><td colspan=\"2\">"+pub.name+"</td></tr>\n");
    });
}
if(c.session){
    $.each(c.session, function(i,ses){
        $("."+j).append("<tr ><td colspan=\"2\"><h3>Session:"+i+"</h3></td></tr>\n");
        $("."+j).append("<tr ><td colspan=\"2\"><h5>URI:</h5></td><td colspan=\"2\">"+ses.URI+"</td></tr>\n");
        $("."+j).append("<tr ><td colspan=\"2\"><h5>Duration:</h5></td><td colspan=\"2\">"+ses.duration.description+"</td></tr>\n");
        $("."+j).append("<tr ><td colspan=\"2\"><h5>Start date:</h5></td><td colspan=\"2\">"+ses.startDate+"</td></tr>\n\</table></div>");
    });
}
j++;
});

    //$('#searchResultDiv').html(projectDiv);
    $('#searchResultDiv').slideDown(500);


}

