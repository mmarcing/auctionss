var auctionPage = 0;

function auctionExistInList(list, auctionId) {
	var exist = false;
	list.forEach(function(entry) {
		if (entry.auctionId === auctionId) {
			exist = true;
		}
	});
	return exist;
}

function searchAuctions(auctionPage) {
	var query = $("#searchQuery").val();
	if (query.length > 0) {
		if (query.length == 0) {
			query = "null";
		}

		$.ajax({
			url : "/searchAuctions/" + query + "/" + auctionPage,
			type : 'GET',
			success : function(response) {
				if (response.length > 0) {
					showAuctions(response);
				} else {
					if (jQuery('#auctions').html().length == 0) {
						if (query != null) {
							var str = '';
							jQuery('#auctions').html(
									'<br/>Brak wyników dla frazy \'' + query
											+ '\'' + str);
						} else {
							jQuery('#auctions').html(
									'<br/>Brak wyników dla wybranych filtrów \''
											+ query + '\'');
						}
					}
				}
			}
		});
	} else {
		if (auctionPage == 0) {
			jQuery('#auctions').html('');
		}
		moreAuctions(auctionPage);
	}
}

function moreAuctions(auctionPage) {
	$.ajax({
		url : "/moreAuctions/" + auctionPage,
		type : 'GET',
		success : function(response) {
			showAuctions(response);
		}
	});
}

$(document).keyup(function(e) {
	if ($(".searchQuery:focus") && (e.keyCode === 13)) {
		searchAuctionBtnClick();
	}
	auctionPage = 0;
});

// ** PROFILE.JSP
function changeAvatar() {
	var file = $('#avatarForm')[0];
	var form = new FormData(file);
	document.getElementById("loader").style.display = "block";
	$.ajax({
		url : "/profil/uploadavatar",
		enctype : 'multipart/form-data',
		type : "POST",
		cache : false,
		contentType : false,
		processData : false,
		data : form,
		success : function(response) {
			document.getElementById("loader").style.display = "none";
			$("#profileAvatar").attr('src', response);
			$("#menuAvatar").attr('src', response);
			$("#avatar").attr('src', response);
			$("#msgAvatar").html("<br/>Zaktualizowano awatar");
			$("#msgAvatarErr").html("");
			$("#messageBasicInfo", null);
			$("#messageBadMailInfo", null);
			$("#messageBankInfo", null);
			$("#messageMailInfo", null);
			scrollToItem("avatar", 0);
		},
		error : function(err) {
			document.getElementById("loader").style.display = "none";
			var msg = err.responseJSON.message;
			$("#msgAvatar").html("");
			$("#msgAvatarErr").html(msg);
		}
	});
}

var auctionId = -1;

function refrAmountsInterval(auction_id, username) {
	auctionId = auction_id;
	refrAmounts(username);
	setInterval(function() {
		refrAmounts(username);
	}, refrOffersIntervalTime * 1000);
	setInterval(
			function() {
				document.getElementById("refrOfferTime").innerText = refrOffersIntervalTime
						- refrOfferTimePassed;
				refrOfferTimePassed += 1;
			}, 1000);
}

function refrAmounts(username) {
	$.get('../../oferty/refreshamounts/' + auctionId, function(data) {
		var lastOfferDivLen = $('#offers').html();

		$('#offers').html(data);

		var newOfferDivLen = $('#offers').html();

		if (lastOfferDivLen != newOfferDivLen) {
			changeOfferDivColor();
		}
	}).fail(function() {
		window.location.href = "/error";
	});

	refrOfferTimePassed = 1;
}

function changeOfferDivColor() {

	var rgb = 135;
	setInterval(function() {
		if (rgb >= 0) {
			document.getElementById("offers").style.background = "rgb(" + rgb
					+ ",255," + rgb + ")";
			rgb += 1;
			if (rgb == 255) {
				rgb = -1;
			}
		}

	}, 10);
}

function deleteOffer(offerId) {
	$.get('../../oferty/usun/' + offerId, function(data) {
		if (data == "success")
			refrAmounts(null);
		else
			window.location.href = "/error";
	}).fail(function() {
		window.location.href = "/error";
	});
}



function addConflictMsgForOffer(msg) {
	var msgVal = msg[0].value;
	$
			.get(
					'../../rozwiazania/dodajKonfliktDlaOferty/'
							+ conflictOfferId + '/' + msgVal,
					function(data) {
						if (data.indexOf("OK") > -1) {
							document.getElementById("divConflictSuccSend").style.display = "block";
							$("#conflMsgAdd").modal();
							conflictOfferId = null;
							document.getElementById("acceptSolBtn").className += " disabled";
							document.getElementById("acceptSolBtn").onclick = "";
							document.getElementById("conflictSolBtn").className += " disabled";
							document.getElementById("conflictSolBtn").onclick = "";
						} else {
							conflictOfferId = null;
							window.location.href = "/error";
						}
					}).fail(function() {
				conflictOfferId = null;
				window.location.href = "/error";
			});
}
