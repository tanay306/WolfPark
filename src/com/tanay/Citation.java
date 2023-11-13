package com.tanay;

public class Citation {

		protected static int citation_number;
		protected static String citation_date;
		protected static String citation_time;
		protected static String category;
		protected static boolean payment_status;
		protected static String lot_name;
		protected static String zone_id;
		protected static String space_number;

		public int getCitation_number() {
			return citation_number;
		}

		public void setCitation_number(int citation_number) {
			this.citation_number = citation_number;
		}

		public String getCitation_date() {
			return citation_date;
		}

		public void setCitation_date(String citation_date) {
			this.citation_date = citation_date;
		}

		public String getCitation_time() {
			return citation_time;
		}

		public void setCitation_time(String citation_time) {
			this.citation_time = citation_time;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public boolean isPayment_status() {
			return payment_status;
		}

		public void setPayment_status(boolean payment_status) {
			this.payment_status = payment_status;
		}

		public String getLot_name() {
			return lot_name;
		}

		public void setLot_name(String lot_name) {
			this.lot_name = lot_name;
		}

		public String getZone_id() {
			return zone_id;
		}

		public void setZone_id(String zone_id) {
			this.zone_id = zone_id;
		}

		public String getSpace_number() {
			return space_number;
		}

		public void setSpace_number(String space_number) {
			this.space_number = space_number;
		}
}
