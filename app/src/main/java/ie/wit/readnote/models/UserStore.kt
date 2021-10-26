package ie.wit.readnote.models

interface UserStore {
    fun findUserById(id: Long) : UserModel?
    fun createUser(user: UserModel)
    fun updateUser(user: UserModel)
    fun deleteUser(user: UserModel)
}