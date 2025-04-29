<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
$(function() {

	var accessToken = "4c7e6801d3eb1c360aeab43bbb4e8e5837aae5d0";



	var params = {
		"long_url" : "https://dev.bitly.com"
	};

	$.ajax({
		url: "https://api-ssl.bitly.com/v4/shorten",
		cache: false,
		dataType: "json",
		method: "POST",
		contentType: "application/json",
		beforeSend: function (xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + accessToken);
		},
		data: JSON.stringify(params)
	}).done(function(data) {
		console.log(data);

		console.log("data.link", data.link);
	}).fail(function(data) {
		console.log(data);
	});
});
</script>





