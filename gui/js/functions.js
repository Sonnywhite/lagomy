function switchVisibility(idToHide,idToDisplay) {
	var _idToHide = "#"+idToHide;
	var _idToDisplay = "#"+idToDisplay;
	$(_idToHide).css("display", "none");
	$(_idToDisplay).css("display", "table-row");
}