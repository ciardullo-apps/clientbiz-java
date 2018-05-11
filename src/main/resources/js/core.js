var sortOrders =  [ 'asc', 'desc' ];
var sortOrderIndex = 1;

function saveClient() {
    //var formData = $('#edit-client').serializeArray().reduce(function(a, x) { a[x.name] = x.value; return a; }, {});
    var formData = {
        "id": $("#id").val(),
        "firstname": $("#firstName").val(),
        "lastname": $("#lastName").val(),
        "topicList": [ $("select#topic_id option:checked").val() ],
        "contactname": $("#contactName").val(),
        "city": $("#city").val(),
        "state": $("#state").val(),
        "timezone": $("#timezone").val(),
        "firstcontact": $("#firstContact").val(),
        "firstresponse": $("#firstResponse").val(),
        "solicited": $("#solicited").val()
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

function clientListController($scope, $http) {
  $scope.formData = {};
  $http.get('/client')
    .success(function(data) {
          $scope.clients = data;
    })
    .error(function(data) {
    });

  $http.get('/topics')
    .success(function(data) {
      $scope.topics = data.topics;
    })
    .error(function(data) {
    });

  $scope.loadClients = function(sortColumn) {
    var config = {
        params: {
          'sortColumn': sortColumn,
          'sortOrder': sortOrders[(sortOrderIndex ^= 1)]
        }
      };
    $http.get('/client', config)
      .success(function(data) {
            $scope.clients = data;
      })
      .error(function(data) {
      });
  }
}

function appointmentListController($scope, $http, $routeParams) {
  var clientId = $routeParams['clientId'];
  $http.get('/appointments/' + clientId)
    .success(function(data) {
      $scope.appointments = data;
    })
    .error(function(data) {
    });

  $http.get('/topics')
    .success(function(data) {
      $scope.topics = data.topics;
    })
    .error(function(data) {
    });

  $http.get('/client/' + clientId)
    .success(function(data) {
      // alert(JSON.stringify(data));

      $scope.client = {
        'client_id': data.clientId,
        'firstname': data.firstname,
        'lastname': data.lastname,
        'contactname': data.contactname,
        'city': data.city,
        'state': data.state,
        'timezone': data.timezone,
        'firstcontact': data.firstcontact,
        'firstresponse': data.firstresponse,
        'solicited': !!+data.solicited
      };

    })
    .error(function(data) {
    });

}

function createAppointmentController($scope, $http, $routeParams) {
  // var clientId = $routeParams['clientId'];
  var clients = { };
  var topics = { };

  $http.get('/client')
    .success(function(data) {
      $scope.clients = data;

      $http.get('/topics')
        .success(function(data) {
          $scope.topics = data.topics;
        })
        .error(function(data) {
      });

    })
    .error(function(data) {
  });

  // Advance to next hour
  var date = new Date();
  var nextHour = new Date(date.getTime() - (date.getTimezoneOffset() * 60000));
  nextHour.setMinutes(0);
  nextHour.setHours(nextHour.getHours() + 1);

  $scope.formData = {
    // 'client_id': 10,
    // 'topic_id': "2",
    'starttime': nextHour.toJSON().slice(0,16),
    'duration': 60,
    'rate': 60,
    'billingpct': 0.80
  };

  $scope.saveAppointment = function() {
    $http({
        method: 'POST',
        url: '/saveAppointment',
        data: $.param($scope.formData), // pass fields as strings
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        } // set the headers so angular passes fields as form data and not request payload
      })
      .success(function(data) {
        console.log(data);
        $http.get('/appointments/' + $scope.formData['client_id'])
          .success(function(data) {
            $scope.appointments = data;
          })
          .error(function(data) {
          });

      });

  }
}

function receivablesController($scope, $http, $routeParams) {
  $scope.updateAppointmentData = {};

  // Default to today
  var date = new Date();
  var nextHour = new Date(date.getTime() - (date.getTimezoneOffset() * 60000));

  $http.get('/receivables')
    .success(function(data) {
      $scope.receivables = data;
      $scope.paiddate =  nextHour.toJSON().slice(0,10);
    })
    .error(function(data) {
    });

  $scope.getPaid = function(appointmentId) {
    $scope.updateAppointmentData["id"] = appointmentId;
    $scope.updateAppointmentData["paid"] = $scope.paiddate ;

    $http({
        method: 'POST',
        url: '/updatePaidDate',
        data: $.param($scope.updateAppointmentData),
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      })
      .success(function(data) {
        console.log(data);
      });
  }
}

function editClientController($scope, $http, $routeParams) {
  var clientId = $routeParams['clientId'];
  var topics = { };
  $http.get('/topics')
    .success(function(data) {
      $scope.topics = data.topics;
    })
    .error(function(data) {
  });

  // Advance to next hour
  var date = new Date();
  var nextHour = new Date(date.getTime() - (date.getTimezoneOffset() * 60000));
  nextHour.setMinutes(0);
  nextHour.setHours(nextHour.getHours() + 1);

  if (clientId) {
    $http.get('/client/' + clientId)
      .success(function(data) {
        // alert(JSON.stringify(data));
        $scope.formData = {
          'id': data.clientId,
          'firstname': data.firstname,
          'lastname': data.lastname,
          'contactname': data.contactname,
          'city': data.city,
          'state': data.state,
          'timezone': data.timezone,
          'firstcontact': data.firstcontact,
          'firstresponse': data.firstresponse,
          'solicited': !!+data.solicited
        };

      })
      .error(function(data) {
      });

  } else {
    $scope.formData = {
      'topic_id': 2,
      'firstcontact': nextHour.toJSON().slice(0,16),
      'firstresponse': nextHour.toJSON().slice(0,16),
      'solicited': "1"
    };
  }
}

