import React from 'react';
import '../CSS/style.module.css'; // assuming the CSS is in a file named Profile.css in the same directory

class Profile extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      name: 'John Doe',
      email: 'john.doe@example.com',
      bio: 'Software Developer at XYZ company',
      imageUrl: 'path_to_image' // replace with the actual image path
    };
  }

  render() {
    return (
      <div className="profile-container">
        <img src={this.state.imageUrl} alt="Profile" className="profile-image" />
        <h2 className="profile-name">{this.state.name}</h2>
        <p className="profile-email"><strong>Email:</strong> {this.state.email}</p>
        <p className="profile-bio"><strong>Bio:</strong> {this.state.bio}</p>
      </div>
    );
  }
}

export default Profile;
