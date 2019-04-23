var sortOrders =  [ 'asc', 'desc' ];
var sortOrderIndex = 1;

function saveClient() {
    //var formData = $('#edit-client').serializeArray().reduce(function(a, x) { a[x.name] = x.value; return a; }, {});
    var formData = {
        "id": $("#id").val(),
        "firstname": $("#firstName").val(),
        "lastname": $("#lastName").val(),
        "topicId": $("#topicId option:selected").val(),
        "contactname": $("#contactName").val(),
        "city": $("#city").val(),
        "state": $("#state").val(),
        "timezone": $("#timezone").val(),
        "firstcontact": $("#firstContact").val(),
        "firstresponse": $("#firstResponse").val(),
        "solicited": $("#solicited1").is(':checked') // TODO Why is Thymeleaf adding 1 to the id?
    }
    console.log(JSON.stringify(formData));

    $.ajax({
        method: 'POST',
        url: '/clientbiz-java/saveClient',
        data: JSON.stringify(formData), // pass fields as strings
        contentType: 'application/json',
        processData: false
      })
      .done(function(data) {
        console.log(data);
      });

}

function getPaid(appointmentId) {
    var formData = {
        "id": appointmentId,
        "paid": $("#paiddate").val()
    }
    console.log(JSON.stringify(formData));

    $.ajax({
        method: 'POST',
        url: '/clientbiz-java/updatePaidDate',
        data: JSON.stringify(formData), // pass fields as strings
        contentType: 'application/json',
        processData: false
      })
      .done(function(data) {
        console.log(data);
      });


}
/*
window.addEventListener("load", function () {
  function saveClient() {
    var XHR = new XMLHttpRequest();

    // Bind the FormData object and the form element
    var FD = new FormData(form);

//    var FD = {"firstName":"John", "lastName":"Doe"};

    // Define what happens on successful data submission
    XHR.addEventListener("load", function(event) {
      alert(event.target.responseText);
    });

    // Define what happens in case of error
    XHR.addEventListener("error", function(event) {
      alert('Oops! Something went wrong.');
    });

    // Set up our request
    XHR.open("POST", document.getElementById("edit-client").action );

    // Add the required HTTP header for form data POST requests
    XHR.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    // The data sent is what the user provided in the form
    XHR.send(FD);
  }

  // Access the form element...
  var form = document.getElementById("edit-client");

  // ...and take over its submit event.

  form.addEventListener("submit", function (event) {
    event.preventDefault();

    saveClient();
  });
});
*/

function loadClients(sortColumn) {
    var formData = {
          sortColumn: sortColumn,
          sortOrder: sortOrders[(sortOrderIndex ^= 1)],
          target: "client-list :: client-list-internal"
    }

    $.ajax({
        method: 'GET',
        url: '/clientbiz-java/client.html',
        data: formData
      })
      .done(function(data) {
        $('#clientList').replaceWith(data);
      });
}

function saveAppointment() {
    var formData = {
        "client_id": $("#clientId option:selected").val(),
        "topic_id": $("#topicId option:selected").val(),
        "starttime": $("#startTime").val(),
        "duration": $("#duration").val(),
        "rate": $("#rate").val(),
        "billingpct": $("#billingPct").val(),
        "description": $("#description").val()
    }
    console.log(JSON.stringify(formData));

    $.ajax({
        method: 'POST',
        url: '/clientbiz-java/saveAppointment',
        data: JSON.stringify(formData), // pass fields as strings
        contentType: 'application/json',
        processData: false
      })
      .done(function(data) {
        console.log(data);
      });

}

