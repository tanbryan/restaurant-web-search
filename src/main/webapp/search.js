function getQueryVariable(variable) {
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        for (var i = 0; i < vars.length; i++) {
            var pair = vars[i].split("=");
            if (pair[0] == variable) {
                return decodeURIComponent(pair[1]);
            }
        }
        return false;
    }

    function displayResults(data) {
        var name = document.getElementById('name').value;
        var latitude = document.getElementById('latitude').value;
        var longitude = document.getElementById('longitude').value;
        var criteria = document.querySelector('input[name="criteria"]:checked').value;
        var resultsContainer = document.getElementById('search-results');
        resultsContainer.innerHTML = ''; // Clear existing results
        
        
	    var resultsHeading;
	   		resultsHeading = document.createElement('h2');
	        resultsHeading.textContent = 'Results for "' + name + '"';
	        localStorage.setItem("searchResultsHeading", resultsHeading.textContent); // Store the heading
	    console.log(resultsHeading.textContent);
	    resultsContainer.appendChild(resultsHeading);
        
        data.forEach(restaurant => {
            var restaurantDiv = document.createElement('div');
            restaurantDiv.className = 'result-item';
            restaurantDiv.innerHTML = `
                <hr style="border: 0.5px solid #ddd ">
                <div class="result-container">
                <a href="detail.html?name=${encodeURIComponent(restaurant.name)}&address=${encodeURIComponent(restaurant.address)}&phone=${encodeURIComponent(restaurant.phone)}&imageLink=${encodeURIComponent(restaurant.imageLink)}&cuisine=${encodeURIComponent(restaurant.cuisine)}&price=${encodeURIComponent(restaurant.price)}&rating=${encodeURIComponent(restaurant.rating)}&yelpLink=${encodeURIComponent(restaurant.yelpLink)}">
                    <img src="${restaurant.imageLink}" alt="${restaurant.name}">
                </a>
                <div>
                    <h3>${restaurant.name}</h3>
                    <p>${restaurant.address}</p>
                    <a href="${restaurant.yelpLink}">${restaurant.yelpLink}</a>
                </div>
                </div>
            `;

            resultsContainer.appendChild(restaurantDiv);
        });

        // Save results to localStorage
        localStorage.setItem("searchResults", JSON.stringify(data));
    }

    document.addEventListener('DOMContentLoaded', function() {
        var nameValue = getQueryVariable("name");
        var latitudeValue = getQueryVariable("latitude");
        var longitudeValue = getQueryVariable("longitude");
        var criteriaValue = getQueryVariable("criteria");
    
        if (nameValue) document.getElementById('name').value = nameValue;
        if (latitudeValue) document.getElementById('latitude').value = latitudeValue;
        if (longitudeValue) document.getElementById('longitude').value = longitudeValue;
        if (criteriaValue) {
            document.querySelector(`input[name="criteria"][value="${criteriaValue}"]`).checked = true;
        }

        document.getElementById('search-form').addEventListener('submit', function(event) {
            event.preventDefault(); // Prevent the default form submission
            localStorage.removeItem("searchResults"); // Clear existing saved results

            // Extract search parameters from the form
            var name = document.getElementById('name').value;
            var latitude = document.getElementById('latitude').value;
            var longitude = document.getElementById('longitude').value;
            var criteria = document.querySelector('input[name="criteria"]:checked').value;

            // Construct the URL for the request
            var url = '/tanbryan_CSCI201_Assignment4/search?name=' + encodeURIComponent(name) + '&latitude=' + encodeURIComponent(latitude) + '&longitude=' + encodeURIComponent(longitude) + '&criteria=' + encodeURIComponent(criteria);
            
            // Fetch the data
            fetch(url)
                .then(response => response.json())
                .then(data => displayResults(data))
                .catch(error => console.error('Error:', error));
        });
    });

    // Check for saved results on page load
    var savedHeading = localStorage.getItem("searchResultsHeading");
    console.log(savedHeading);
        var resultsHeading = document.getElementById('results-heading');
        if (resultsHeading) {
            resultsHeading.textContent = savedHeading;
        }
    
    
    var savedResults = localStorage.getItem("searchResults");
    console.log("saved:"+ savedResults);
    localStorage.clear();
    console.log("saved:"+ savedResults);
    if (savedResults) {
        displayResults(JSON.parse(savedResults));
    }
    
    
    