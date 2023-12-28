export class BookmarkedPost {
    #PostId
    #UserId
    constructor (postId,userId){
        this.#PostId = postId
        this.#UserId = userId
    }
    getPostId(){return this.#PostId}
    getUserId(){return this.#UserId}
    setPostId(id){this.#PostId = id}
    setUserId(id){this.#UserId = id}
    
}