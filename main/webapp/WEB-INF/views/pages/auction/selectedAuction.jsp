<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<script type="text/javascript">
	window.onload = function() {
		refrAmountsInterval('${auction.id}', '${logUsername}');
	}
</script>


<br />
<div class="row">
	<div class="col-sm-3" style="margin-bottom: 20px;">
		<div class="card">
			<div
				class="card-header text-center card-inverse card-header-black-background">
				<sec:authorize access="isAuthenticated()">
					<div style="font-size: 13px;">
						Stan konta <span id="pocketvalue"></span> zł
					</div>
				</sec:authorize>
				<div style="display: inline-block;">Oferty</div>
				<div id="" class="reftOfferTime">
					Odświeżenie za:
					<div id="refrOfferTime" style="display: inline-block;">30</div> s 
				</div>
			</div>
			<div id="offers"></div>
		</div>
		<sec:authorize access="isAuthenticated()">
			<c:if test="${auction.user.username != pageContext.request.userPrincipal.name}">
				<button class="btn btn-lg btn-primary btn-block"
					onclick="addOfferClick('${pageContext.request.userPrincipal.name}');">Dodaj
					ofertę</button>
				<div class="modal fade" id="myOfferModal">
					<form class="form" method="GET"
						action="${contextPath}/oferty/dodaj/${auction.id}/${amount}"
						style="padding-top: 5px;">
						<div class="modal-dialog modal-sm" role="document">
							<div class="modal-content">
								<div class="modal-body">
									<div style="text-align: center;">
										<input type="number" class="form-control" id="ofAmount"
											name="amount" min="1" max="100000"
											style="width: 89%; display: inline-block;" />
										<p style="width: 9%; display: inline-block;">zł</p>
									</div>
								</div>
								<div style="display: block; margin: auto;">
									<button id="sendoffer" type="submit" class="btn btn-primary">Dodaj
										ofertę</button>
									<button type="button" class="btn btn-secondary"
										data-dismiss="modal">Zamknij</button>
								</div>
								<div class="modal-footer"
									style="text-align: center; margin-top: 15px;">
									<p style="font-size: 10px;">W momencie, gdy ...</p>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal fade" id="offerExistModal">
					<div class="modal-dialog modal-sm" role="document">
						<div class="modal-content">
							<div class="modal-body">
								<div style="text-align: center;">
									<p>Już wystawiłeś ofertę w tej aukcji.</p>
									<p>
										Możesz ją usunąć za pomocą przycisku <b>Anuluj</b>
									</p>
								</div>
								<div style="display: block; margin: auto;">
									<button type="button" class="btn btn-secondary w-100"
										data-dismiss="modal">Zamknij</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:if>
		</sec:authorize>
	</div>
	<div class="col-sm-9">
		<div class="card">
			<div
				class="card-header text-center card-inverse card-header-black-background">
				 ${auction.topic }

				<tags:localDate date="${auction.createDate }" />
			</div>

			<div class="card-block">
				<div class="col-sm-offset-1 col-sm-12">content</div>
			</div>
		</div>
		<sec:authorize access="isAuthenticated()">
			<c:if test="${auction.user.username == pageContext.request.userPrincipal.name && auction.editable}">
				<a class="btn btn-lg btn-primary btn-block editAuctionButton"
					href="/aukcje/edytuj/${auction.id}">Edytuj</a>
				<br />
			</c:if>
		</sec:authorize>
		
	</div>
</div>

<div class="modal fade" id="offerNoMoneyModal">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="modal-body">
				<div style="text-align: center;">
					<p>Nie posiadasz wystarczających środków, aby wybrać tę ofertę.</p>
					<p>
						Twój stan portfela to: <b><span id="pocVal"></span> zł</b>
					<p>
						Brakuje: <b><span id="diffVal"></span> zł</b>
					<p>
						<a href="/doladowanie"><b>Doładuj</b></a>
					</p>
				</div>
				<div style="display: block; margin: auto;">
					<button type="button" class="btn btn-secondary w-100"
						data-dismiss="modal">Zamknij</button>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="askForAcceptOfferModal">
	<div class="modal-dialog modal-sm modal-qo" role="document">
		<div class="modal-content">
			<div class="modal-body">
				<div style="text-align: center;">
					<h5>
						<b> Czy na pewno chcesz wybrać tę ofertę? </b>
					</h5>
					<p>...</p>
				</div>
				<div style="display: block; margin: auto; text-align: center;">
					<button type="button" class="btn btn-success w-30"
						data-dismiss="modal" onclick="acceptOffer();">TAK</button>
					<button type="button" class="btn btn-secondary w-30"
						data-dismiss="modal">NIE</button>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="askForAcceptMoreOffersModal">
	<div class="modal-dialog modal-sm modal-qo" role="document">
		<div class="modal-content">
			<div class="modal-body">
				<div style="text-align: center;">
					<h4 style="color: red;">
						<b>Uwaga!</b>
					</h4>
					<h5>
						<b>Czy na pewno chcesz wybrać kolejną ofertę? </b>
					</h5>
					<p>...</p>
				</div>
				<div style="display: block; margin: auto; text-align: center;">
					<button type="button" class="btn btn-success w-30"
						data-dismiss="modal" onclick="acceptOffer();">TAK</button>
					<button type="button" class="btn btn-secondary w-30"
						data-dismiss="modal">NIE</button>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="newConflictModal">
	<div class="modal-dialog modal-sm modal-qo" role="document">
		<div class="modal-content">
			<div class="modal-body">
				<div style="text-align: center;">
					<h5>
						<b>Podaj powód założenia sporu </b>
					</h5>
					<div  id="conflict">
						<textarea class="form-control" id="conflReason" name="conflReason"
							maxlength="1000" ></textarea>
						<br/>
						<button type="button" class="btn btn-danger sendConflictSolBtn"
							id="sendConflictSolBtn" data-dismiss="modal"
							onclick="addConflictMsgForOffer($(conflReason));"
							value="">Zgłoś</button>
						<button type="button" class="btn sendConflictSolBtn " data-dismiss="modal"
							value="">Zamknij</button>
						<div class="form-group successMsg" id="divConflictSuccSend">
							Konflikt zgłoszony.... <a href="/">spory</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="allConflictsModal">
	<div class="modal-dialog modal-sm modal-qo" role="document">
		<div class="modal-content">
			<div class="modal-body">
				<div style="text-align: center;">
					<h4 style="color: red;">
						<b>Spór</b>
					</h4>
					<c:if test="${conflMsgs.size() > 0}">
						<c:forEach items="${conflMsgs}" var="cMsg">
							<div class="card"
								style="background-color =  ${cMsg.username == auction.user.username 'yellow' : 'green'}">
								<div
									class="card-header text-center card-inverse card-header-grey-background">
									Wiadomość ${cMsg.username } (
									<tags:localDate date="${cMsg.createDate }" />
									)
								</div>
								<div class="card card-block">
									<div class="col-sm-offset-1 col-sm-12">${cMsg.message}</div>
								</div>
							</div>
							<br />
						</c:forEach>
					</c:if>

					<div class="collapse" id="conflict">
						<input class="form-control" id="conflMsg" name="conflMsg"
							maxlength="1000" />
						<button type="button" class="btn btn-danger sendConflictSolBtn"
							id="sendNextConflictSolBtn" data-dismiss="modal"
							onclick="addConflictMsgForOffer(${solution.offer_id}, ${conflMsg});"
							value="">Dodaj wiadomość</button>
						<button type="button" class="btn btn-danger " data-dismiss="modal"
							value="">Zamknij</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="conflMsgAdd">
	<div class="modal-dialog modal-sm modal-qo" role="document">
		<div class="modal-content">
			<div class="modal-body">
				<div style="text-align: center;">
					<h4 style="color: red;">
						<b>Dodano wiadomość do sporu</b>
					</h4>
					<button type="button" class="btn btn-danger " data-dismiss="modal"
						value="">Zamknij</button>
				</div>
			</div>
		</div>
	</div>
</div>