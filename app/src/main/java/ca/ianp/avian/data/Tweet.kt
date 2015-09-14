package ca.ianp.avian.data

import android.os.Parcel
import android.os.Parcelable

data class Tweet(p: Parcel) : Parcelable {

    var id: Long
    var authorName: String
    var authorScreenName: String
    var avatar: String
    var createdAt: String
    var content: String


    init {
        id = p.readLong()
        authorName = p.readString()
        authorScreenName = p.readString()
        avatar = p.readString()
        createdAt = p.readString()
        content = p.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id)
        dest.writeString(authorName)
        dest.writeString(authorScreenName)
        dest.writeString(avatar)
        dest.writeString(createdAt)
        dest.writeString(content)
    }

    companion object {

        public val CREATOR: Parcelable.Creator<Tweet> = object : Parcelable.Creator<Tweet> {
            override fun createFromParcel(p: Parcel): Tweet {
                return Tweet(p)
            }

            override fun newArray(size: Int): Array<Tweet> {
                throw UnsupportedOperationException()
            }
        }
    }
}