export class User{
    static #user = null
    #id
    #user_name
    #email
    #is_admin
    set_id(id){this.#id = id}
    set_user_name(user_name){this.#user_name= user_name}
    set_email(email){this.#email = email}
    set_is_admin(is_admin){this.#is_admin = is_admin}

    get_id(){return this.#id}
    get_user_name(){return this.#user_name}
    get_email(){return this.#email}
    get_is_admin(){return this.#is_admin}


    static getUser(){
        if(this.#user == null){
            this.#user = new User()
        }
        return this.#user
    }
}