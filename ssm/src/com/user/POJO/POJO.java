package com.user.POJO;

public class POJO {
	public class Category {
		private int id;
		private String username;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return username;
		}

		public void setName(String name) {
			this.username = name;
		}

		@Override
		public String toString() {
			return "Category [id=" + id + ", name=" + username + "]";
		}

	}
}
