sap.ui.define([ 'sap/ui/core/mvc/Controller', 'sap/ui/model/json/JSONModel',
		'sap/ui/model/Filter', 'sap/m/MessageBox', "sap/ui/core/BusyIndicator", "sap/m/MessageToast" ], function(Controller,
		JSONModel, Filter, MessageBox, BusyIndicator, MessageToast) {
	"use strict";

	return Controller.extend(
			"com.sap.cloud.ariba.public.sourcing.controller.HomePage", {

				onInit : function() {
					this._setEventsModel();
				},

				_setEventsModel : function() {
					var model = new JSONModel();
					model.loadData("/api/v1/events", "", false);

					var data = model.getProperty("/");
					for (var i = 0; i < data.length; i++) {
						model.setProperty("/" + i + "/visible", false);

						var categoriesStr = data[i].productCategories.map(
								function(item) {
									return item['name'];
								}).join(", ");

						model.setProperty("/" + i + "/categoriesStr",
								categoriesStr);
					}
					this.getView().setModel(model);
				},

				onItemPress : function(oEvent) {
					var sPath = oEvent.getParameter('listItem')
							.getBindingContext().sPath;
					var oModel = oEvent.getParameter('listItem').getModel();
					oModel.setProperty(sPath + "/visible", !oModel
							.getProperty(sPath).visible);
					oModel.refresh();
					oEvent.getSource().rerender();
				},

				handleLinkObjectAttributePress : function(oEvent) {
					var sPath = oEvent.getSource().getParent()
							.getBindingContext().sPath;
					sap.m.URLHelper.redirect(this.getView().getModel()
							.getProperty(sPath).discoveryURL, true);
				},

				handleTermsAndConditionPress : function(oEvent) {
					jQuery.sap.require("jquery.sap.resources");
					var sLocale = sap.ui.getCore().getConfiguration()
							.getLanguage();
					var oBundle = jQuery.sap.resources({
						url : "public-sourcing/i18n/i18n.properties",
						locale : sLocale
					});
					MessageBox.confirm(oBundle
							.getText("termsAndConditionsMessage"), {
						title : oBundle.getText("termsAndConditionsLabel")
					});
				},

				onDatePickerPress : function(oEvent) {
					var sFrom = oEvent.getParameter("from");
					var sTo = oEvent.getParameter("to");
					sTo.setDate(sTo.getDate() + 1);
					var bValid = oEvent.getParameter("valid");
					var oList = this.getView().byId("list");
					var oBinding = oList.getBinding("items");
					var oFilter = new Filter("startDate", "BT", sFrom
							.toISOString(), sTo.toISOString());
					var aFilters = [];
					aFilters.push(oFilter);
					oBinding.filter(aFilters);
				},

				onRefreshPress : function(oEvent) {
					BusyIndicator.show(0);
					var oModel = this.getView().getModel();
					var aData = jQuery.ajax({
						type : "POST",
						url : "/api/v1/eventProcessor/process/",
						async : true,
						success : function() {
							BusyIndicator.hide();
							location.reload();
						},
						error : function() {
							MessageToast.show("Retrieving new events from Discovery RFX Publication to External Marketplace API failed.");
							BusyIndicator.hide();
						}
					});
				}
			});
});
