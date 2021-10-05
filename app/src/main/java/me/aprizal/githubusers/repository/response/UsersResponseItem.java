package me.aprizal.githubusers.repository.response;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class UsersResponseItem implements Parcelable {

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	@SerializedName("id")
	private int id;

	@ColumnInfo(name = "avatar_url")
	@SerializedName("avatar_url")
	private String avatarUrl;

	@ColumnInfo(name = "html_url")
	@SerializedName("html_url")
	private String htmlUrl;

	@ColumnInfo(name = "login")
	@SerializedName("login")
	private String login;

	@ColumnInfo(name = "name")
	@SerializedName("name")
	private String name;

	@ColumnInfo(name = "company")
	@SerializedName("company")
	private String company;

	@ColumnInfo(name = "location")
	@SerializedName("location")
	private String location;

	@ColumnInfo(name = "public_repos")
	@SerializedName("public_repos")
	private int publicRepos;

	public UsersResponseItem() {
	}

	protected UsersResponseItem(Parcel in) {
		id = in.readInt();
		avatarUrl = in.readString();
		htmlUrl = in.readString();
		login = in.readString();
		name = in.readString();
		company = in.readString();
		location = in.readString();
		publicRepos = in.readInt();
	}

	public static final Creator<UsersResponseItem> CREATOR = new Creator<UsersResponseItem>() {
		@Override
		public UsersResponseItem createFromParcel(Parcel in) {
			return new UsersResponseItem(in);
		}

		@Override
		public UsersResponseItem[] newArray(int size) {
			return new UsersResponseItem[size];
		}
	};

	public UsersResponseItem(int id, String avatarUrl, String htmlUrl, String login, String name, String company, String location, int publicRepos) {
		this.id = id;
		this.avatarUrl = avatarUrl;
		this.htmlUrl = htmlUrl;
		this.login = login;
		this.name = name;
		this.company = company;
		this.location = location;
		this.publicRepos = publicRepos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getPublicRepos() {
		return publicRepos;
	}

	public void setPublicRepos(int publicRepos) {
		this.publicRepos = publicRepos;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(avatarUrl);
		dest.writeString(htmlUrl);
		dest.writeString(login);
		dest.writeString(name);
		dest.writeString(company);
		dest.writeString(location);
		dest.writeInt(publicRepos);
	}
}