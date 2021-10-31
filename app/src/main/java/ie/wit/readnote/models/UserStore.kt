package ie.wit.readnote.models

interface UserStore {
    fun createUser(user: UserModel)
    fun deleteUser(user: UserModel)
}