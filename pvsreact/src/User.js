export class User {
    static #user = null
    #id
    #user_name
    #email
    #is_admin
    #join_date
    #token
    #decode
    #image
    set_id(id) { this.#id = id }
    set_user_name(user_name) { this.#user_name = user_name }
    set_email(email) { this.#email = email }
    set_is_admin(is_admin) { this.#is_admin = is_admin }
    set_join_date(join_date) {
        this.join_date = join_date
    }
    set_token(token) {
        this.token = token
    }
    set_decode(decode) {
        this.decode = decode
    }
    set_image(image) {
        this.image = image
    }

    get_id() { return this.#id }
    get_user_name() { return this.#user_name }
    get_email() { return this.#email }
    get_is_admin() { return this.#is_admin }
    get_join_date() {
        return this.join_date
    }
    get_token() {
        return this.token
    }
    get_decode() {
        return this.decode
    }
    get_image() {
        return this.image
    }

    static getUser() {
        if (this.#user == null) {
            this.#user = new User()
        }
        return this.#user
    }
}