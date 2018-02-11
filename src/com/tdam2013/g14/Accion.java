package com.tdam2013.g14;

	public class Accion {

		int _id;
		String _fecha;
		String _contacto;
		String textoSms;
		int _tipo;

		public Accion(int _id, String _fecha, String _contacto, String textoSms,
				int _tipo) {
			super();
			this._id = _id;
			this._fecha = _fecha;
			this._contacto = _contacto;
			this.textoSms = textoSms;
			this._tipo = _tipo;
		}

		public int get_id() {
			return _id;
		}

		public void set_id(int _id) {
			this._id = _id;
		}

		public String get_fecha() {
			return _fecha;
		}

		public void set_fecha(String _fecha) {
			this._fecha = _fecha;
		}

		public String get_contacto() {
			return _contacto;
		}

		public void set_contacto(String _contacto) {
			this._contacto = _contacto;
		}

		public String getTextoSms() {
			return textoSms;
		}

		public void setTextoSms(String textoSms) {
			this.textoSms = textoSms;
		}

		public int get_tipo() {
			return _tipo;
		}

		public void set_tipo(int _tipo) {
			this._tipo = _tipo;
		}
		
		

	}

