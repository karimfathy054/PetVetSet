import React from 'react';
import image from '../images/User/maleUser.png'
import styles from "../CSS/style.module.css"
import '../CSS/style.module.css'; // assuming the CSS is in a file named Profile.css in the same directory

class Profile extends React.Component {
  constructor(props) {
    super(props);
    this.changeName = this.changeName.bind(this)
    this.handleChange = this.handleChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
    this.state = {
      name: 'Omar Tarek Abdelwahab',
      email: 'john.doe@example.com',
      bio: 'Software Developer at XYZ company',
      imageUrl: image, // replace with the actual image path
      changeNameButton: true,
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
    this.setState({name: this.state.nameValue, nameValue: '', changeNameButton: true})
    console.log("name:",this.state.name)
    console.log("name:",this.state.nameValue)
    event.preventDefault()
  }
  render() {
    const isChangeNameButton = this.state.changeNameButton;
    let button;
    if (isChangeNameButton) {
      button = <button onClick= {this.changeName} className={styles.change_name_button}>Change name</button>
    } else {
      button =  <form onSubmit={this.handleSubmit}>
                  <label>
                    Name:
                    <input type="text" value={this.state.value} onChange={this.handleChange} />
                  </label>
                  <input type="submit" value="Submit" />
                </form>
    }
    return (
      <div className={styles.profile_container}>
        <div className={styles.profile_left}>
          <div className= {styles.profile_image_div}>
            <img className={styles.profile_image} src= {this.state.imageUrl} alt='image'/>
          </div>
          <hr/>
          <h2 className= {styles.profile_header}>Name</h2>
          <h2 className={styles.profile_name}>{this.state.name}</h2>
          {button}
          <hr/>
          <h2 className= {styles.profile_header}>Email</h2>
          <h2 className={styles.profile_email}>{this.state.email}</h2>
          <h2 className={styles.profile_email}>{this.state.joinDateZz}</h2>
        </div>
        <div className={styles.profile_right}>
          <p>Right</p>
        </div>
      </div>
    );
  }
}

export default Profile;
