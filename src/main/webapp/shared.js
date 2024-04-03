
		
        function initMap() {
		    var map = new google.maps.Map(document.getElementById('map'), {
		        center: { lat: 34.02116, lng: -118.287132 },
		        zoom: 4
		    });
			var marker = new google.maps.Marker({
		        position: { lat: 34.02116, lng: -118.287132 },
		        map: map,
		    });
		    
		    map.addListener('click', function(event) {
		        var clickedLocation = event.latLng;
		
		        console.log("Clicked location: ", clickedLocation.lat(), clickedLocation.lng());
		
		        document.getElementById('latitude').value = clickedLocation.lat().toFixed(5);
		        document.getElementById('longitude').value = clickedLocation.lng().toFixed(5);
		        
		        document.getElementById('map').style.display = 'none';
		        document.getElementById('overlay').style.display = 'none';
		    });
		}


        
        document.getElementById('google-maps-btn').addEventListener('click', function() {
            var mapElement = document.getElementById('map');
            var overlayElement = document.getElementById('overlay');

            
            mapElement.style.display = 'block';
            overlayElement.style.display = 'block';

            initMap(); 
        });
        document.getElementById('overlay').addEventListener('click', function() {
            document.getElementById('map').style.display = 'none';
            this.style.display = 'none'; 
        });
		
		function getCookie(name) {
		    const cDecoded = decodeURIComponent(document.cookie); 
		    const cArray = cDecoded.split('; '); 
		    let result = null;
		
		    cArray.forEach(element => {
		        let [cookieName, cookieValue] = element.split('=');
		        if (cookieName === name) {
		            result = cookieValue;
		        }
		    });
		    return result;
		}
		
		const navold = document.getElementById("navigation");
		const navnew = document.getElementById("navigation-log");
		
		var userLoggedIn = getCookie("userLoggedIn");
		console.log("Cookie value of userLoggedIn: ", userLoggedIn);
		if (userLoggedIn === "true") {
		    navnew.style.display = "block";
		    navold.style.display = "none";
		    
		} else {
		    navnew.style.display = "none";
		    navold.style.display = "block";
		}
		
		const logoutBtn = document.querySelector("#logout-btn");
			logoutBtn.addEventListener("click", () => {
			    deleteCookie("userLoggedIn");
			    deleteCookie("userId");
			    console.log("Logout clicked");
			});
			
			
			function deleteCookie(name){
				console.log("Deleting cookie: ", name);
			    setCookie(name, null, null);
			}
			
			function setCookie(name, value, daysToLive){
			    const date = new Date();
			    date.setTime(date.getTime() +  (daysToLive * 24 * 60 * 60 * 1000));
			    let expires = "expires=" + date.toUTCString();
			    document.cookie = `${name}=${value}; ${expires}; path=/tanbryan_CSCI201_Assignment4`
			    console.log("Setting cookie: ", document.cookie);
			}

