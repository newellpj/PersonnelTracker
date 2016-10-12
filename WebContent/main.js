'use strict';

var isPushEnabled = false;
var regGlobal;
var sub;
var isSubscribed = false;


if ('serviceWorker' in navigator) { 
	console.log('Service Worker is supported');
	
	navigator.serviceWorker.register('sw.js').then(function(){ 
	
		return navigator.serviceWorker.ready; }).then(function(reg){ 
		 Notification.requestPermission();
			console.log('Service Worker is ready :^)', reg); 
			regGlobal = reg;
			reg.pushManager.subscribe({userVisibleOnly: true}).then(function(sub){ 
			
				console.log('endpoint:', sub.endpoint); }); }).catch(function(error){ 
				
					console.log('Service Worker error :^(', error); }); 
	}





function subscribe() {  
  // Disable the button so it can't be changed while  
  // we process the permission request  
  var pushButton = $('.js-push-button');  
  $(pushButton).prop('disabled', true);

  navigator.serviceWorker.ready.then(function(serviceWorkerRegistration) {  
  
	console.log("navigator service worker ready");
    
	var btnTxt = $(pushButton).html();
	console.log(btnTxt);
	
	if(btnTxt.trim() == 'Enable Push Messages'){
			console.log("in enable");
			serviceWorkerRegistration.pushManager.subscribe({userVisibleOnly: true}).then(function(subscription) {  
			  
			  Notification.requestPermission();
			  console.log('endpoint from push button:', subscription.endpoint);
			  
				// The subscription was successful  
				isPushEnabled = true;  
				$(pushButton).html('Disable Push Messages');  
				$(pushButton).prop('disabled', false);
		
				// TODO: Send the subscription.endpoint to your server  
				// and save it to send a push message at a later date
				
				//fetch API here
				
				console.log("subscription key : "+subscription.getKey('p256dh'));
				
				var endpoint = subscription.endpoint;
				var key = subscription.getKey('p256dh');
				updateStatus(endpoint,key,'subscribe');
				
			   // return sendSubscriptionToServer(subscription);  
			  })  
			  .catch(function(e) {  
				if (Notification.permission === 'denied') {  
				  // The user denied the notification permission which  
				  // means we failed to subscribe and the user will need  
				  // to manually change the notification permission to  
				  // subscribe to push messages  
				  console.warn('Permission for Notifications was denied');  
				  pushButton.disabled = true;  
				} else {  
				  // A problem occurred with the subscription; common reasons  
				  // include network errors, and lacking gcm_sender_id and/or  
				  // gcm_user_visible_only in the manifest.  
				  console.error('Unable to subscribe to push.', e);  
				  pushButton.disabled = false;  
				  pushButton.textContent = 'Disable Push Messages';  
				}  
			  });  
		}else{
			console.log("in disable");
			serviceWorkerRegistration.pushManager.getSubscription().then(function(subscription) { 
		
			  subscription.unsubscribe().then(function(successful){
				  
				console.log('The un-subscription was successful ');
					isPushEnabled = true;  
					$(pushButton).html('Enable Push Messages');  
					$(pushButton).prop('disabled', false);
		

				// TODO: Send the unsubscription.endpoint to your server  
				// and save it to send a push message at a later date
				
				//fetch API here
					updateStatus(endpoint,key,'unsubscribe');
			   // return sendSubscriptionToServer(subscription);  
			  })  
			  .catch(function(e) {  
				if (Notification.permission === 'denied') {  
				  // The user denied the notification permission which  
				  // means we failed to subscribe and the user will need  
				  // to manually change the notification permission to  
				  // subscribe to push messages  
				  console.warn('Permission for Notifications was denied');  
				  pushButton.disabled = true;  
				} else {  
				  // A problem occurred with the subscription; common reasons  
				  // include network errors, and lacking gcm_sender_id and/or  
				  // gcm_user_visible_only in the manifest.  
				  console.error('Unable to unsubscribe from push.', e);  
				  pushButton.disabled = false;  
				  pushButton.textContent = 'Enable Push Messages';  
				}  
			  }); 
			 }); 
			
		}
  });  
}


function updateStatus(endpoint,key,statusType) {
    console.log("updateStatus, endpoint: " + endpoint);
    console.log("updateStatus, key: " + key);
    
	  // If we are subscribing to push
	  if(statusType === 'subscribe' || statusType === 'init') {


		sendSubscriptionDetailsToServer('subscribe', null, endpoint, key);

	  } else if(statusType === 'unsubscribe') {
	
		sendSubscriptionDetailsToServer('unsubscribe', null, endpoint, key);

	  }

}


function sendSubscriptionDetailsToServer(statusType, username, endpoint, key){
	
			$.ajax({
				url: 'addSubscription',
				dataType: 'JSON',
				data: { 
					statusType: statusType,
					username: username,
					endpoint: endpoint,
					key: key
				},
				processData: true,
				contentType: 'application/json; charset=utf-8',
				type: 'GET',
				success:  function(response) {
						    console.log('successful ');
				 },

			 error: function(e){


					var errorDialog = $("<div></div>").dialog({
							hide: 'fade',
							maxWidth: 300,
							modal: true,
							show: 'fade',
							open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
								buttons: [
							{
								'class': 'btn btn-primary',
								click: function(e) {

									$(this).dialog("close");

								},
								text: 'OK'
							}
						
						],	
							title: 'Could NOT add subscription',
							width: ( 300 )
						});

						var msg = "Could NOT add subscription please try again!";
						
						if('undefined' == msg || msg == null){
								msg = "There was an error retrieving the feed";
						}
						
						$(errorDialog).html('<p>'+msg+'</p>');
				        $('.ui-dialog-buttonset').css("backgroundImage", "url('')");
				        $('.ui-dialog-buttonset').css("backgroundColor", "#c3c3c3");
						 $(errorDialog).dialog("open");
					
			 }
			});
}


