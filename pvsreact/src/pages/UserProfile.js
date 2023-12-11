import React from 'react';
import image from '../images/User/maleUser.png'
import styles from "../CSS/style.module.css"
import '../CSS/style.module.css'; // assuming the CSS is in a file named Profile.css in the same directory
// import {User} from "../User.js"
class Profile extends React.Component {
  constructor(props) {
    super(props);
    this.changeName = this.changeName.bind(this)
    this.handleChange = this.handleChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
    this.cancelNamChange = this.cancelNamChange.bind(this)
    // this.user = User.getUser()
    this.state = {
      // name: this.user.get_user_name(),
      // email: this.user.get_email(),
      name: "Omar Tarek Abdelwahab",
      email: "omar@pet.com",
      imageUrl: image, // replace with the actual image path
      changeNameButton: true,
      changeNameMessage: "Enter Name:",
      nameValue: '',
      joinDate: '2023-12-10'
    };
  }
  changeName(){
    this.setState({changeNameButton: false})
    console.log("changed")
  }
  handleChange(event){
    this.setState({nameValue: event.target.value})
  }
  handleSubmit(event){
    if(this.state.nameValue.length <= 2 ){
      this.setState({changeNameMessage: "Name can't be less than 2 characters"})
      event.preventDefault()
    }else{
      this.setState({changeNameMessage: "Enter Name:"})
      this.setState({name: this.state.nameValue, nameValue: '', changeNameButton: true})
      console.log("name:",this.state.name)
      console.log("name:",this.state.nameValue)
      event.preventDefault()
    }
  }
  cancelNamChange(){
    this.setState({changeNameMessage: "Enter Name:", changeNameButton: true, nameValue: ''})
  }
  render() {
    const isChangeNameButton = this.state.changeNameButton;
    let button;
    if (isChangeNameButton) {
      button = <button onClick= {this.changeName} className={styles.change_name_button}>Change name</button>
    } else {
      button =  <form onSubmit={this.handleSubmit}>
                  <label>
                    {this.state.changeNameMessage}:
                    <input type="text" onChange={this.handleChange} />
                  </label>
                  <input type="submit" value="Submit" />
                  <button onClick={this.cancelNamChange}>cancel</button>
                </form>
    }
    return (
      <div className={styles.profile_container}>
        <div className={styles.profile_left}>
          <div className= {styles.profile_image_div}>
            <img className={styles.profile_image} src= {this.state.imageUrl} alt='User'/>
          </div>
          <hr className={styles.profile_line_break}/>
          <h2 className= {styles.profile_header}>Name</h2>
          <h2 className={styles.profile_name}>{this.state.name}</h2>
          {button}
          <hr className={styles.profile_line_break}/>
          <h2 className= {styles.profile_header}>Email</h2>
          <h2 className={styles.profile_email}>{this.state.email}</h2>
          <hr className={styles.profile_line_break}/>
          <h2 className= {styles.profile_header}>Joined:</h2>
          <h2 className={styles.profile_email}>{this.state.joinDate}</h2>
        </div>
        <div className={styles.profile_right}>
          <p>Right</p>
        </div>
      </div>
    );
  }
}

export default Profile;
