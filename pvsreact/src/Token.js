import { Component } from "react";
class Token extends Component {
    constructor() {
        super();
        this.state = {
            token: '',
            decode: {},
        };
    }
    handleToken(newToken, newDecode) {
        this.setState({
            token: newToken,
            decode: newDecode,
        })
    }
}
export default Token;