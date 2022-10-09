package me.davidsonsilva.core.data

object DbConstants {
    const val APP_DATABASE_NAME = "app_database"
    /*Favorites DB COnstants*/
    const val FAVORITES_TABLE_NAME = "favorites"
    const val FAVORITES_COLUMN_INFO_ID = "id"
    const val FAVORITES_COLUMN_INFO_NAME = "name"
    const val FAVORITES_COLUMN_INFO_IMAGE_URL = "image_url"
    /*Characters DB Constants*/
    const val CHARACTERS_TABLE_NAME = "characters"
    const val CHARACTERS_COLUMN_INFO_ID = "id"
    const val CHARACTERS_COLUMN_INFO_NAME = "name"
    const val CHARACTERS_COLUMN_INFO_IMAGE_URL = "image_url"
    /*Remote Keys*/
    const val REMOTE_KEYS_TABLE_NAME = "remote_keys"
    const val REMOTE_KEYS_COLUMN_INFO_OFFSET = "next_offset"
}