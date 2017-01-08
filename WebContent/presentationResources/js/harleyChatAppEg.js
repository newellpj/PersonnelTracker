// Welcome to the General Assembly Mega Awesome Chat App!
// This template includes all the HTML we'll need to get
// a cool app up and running. Let's get started!

angular.module('chatApp', ['firebase'])
.component('chatRoom', {
  template: `
    <div class="messages row">
      <div class="small-12 medium-6 medium-offset-3 end columns">
        <ul class="messages-list no-bullet">
          <li ng-repeat="message in $ctrl.messages.data 
          | orderBy:created:true ">
            <message data="message"></message>
          </li>
        </ul>
        <message-form on-new-message="$ctrl.messages.add(value)"></message-form>
      </div>
    </div>
  `,
  controller: function(Messages){
    this.messages = Messages;
  }
})
.component('messageForm', {
  template: `
    <form class="write-box" ng-submit="$ctrl.send()">
      <div class="row collapse postfix">
        <div class="small-9 columns">
          <input type="text" ng-model="$ctrl.message" />
        </div>
        <div class="small-3 columns">
          <button class="button expanded">Send</button>
        </div>
    </form>
  `,
  bindings: { onNewMessage: '&' },
  controller: function(){
    var $ctrl = this;
    $ctrl.message = '';
    $ctrl.send = function(){
      $ctrl.onNewMessage({
        value: 
        { name: 'Harley', message: $ctrl.message, created: Date.now() }
      });
      this.message = '';
    }
  }
})
.component('message', {
  template: `
    <div class="warning callout">
      <strong>{{$ctrl.data.name}}</strong> 
      {{$ctrl.data.created | date:'h:mma'}}
      <p>{{$ctrl.data.message}}</p>
    </div>
  `,
  bindings: { data: '<' }
})
.service('Messages', function($firebaseArray){
  var ref = firebase.database().ref().child('messages');
  var Messages = {
    data: $firebaseArray(ref),
    add: (message) => Messages.data.$add(message)
  };
  
  return Messages;
})


// Ignore this for now. We'll use it later.
var config = {
    apiKey: "AIzaSyDmGB2Kqm_BzuR3stzIRXfBhTAS2NOFRIM",
    authDomain: "general-assembly.firebaseapp.com",
    databaseURL: "https://general-assembly.firebaseio.com",
    storageBucket: "",
    messagingSenderId: "15436405814"
};
firebase.initializeApp(config);





<html>

<head>
  <title>Awesome todo app</title>
</head>

<body ng-app="chatApp">
  <chat-room></chat-room>
</body>
</html>




.messages-list{
  height: 400px;
  overflow: scroll;
  padding: 1rem 1rem 0;
}