# React forms

* [React Forms](https://facebook.github.io/react/docs/forms.html)

## Controlled Components

```js
import React, { Component } from 'react';
import './Form.css';

class Form extends React.Component {
  constructor(props) {
    super(props);
    
    this.state = {
      name: '',
      description: '',
      legal: '',
      gender: '',
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {
    const target = event.target;
    const value = (target.type === 'checkbox') ? target.checked : target.value;
    const name = target.name;

    this.setState({
      [name]: value
    });
  }

  handleSubmit(event) {
    console.log('A name was submitted: ', this.state);
    event.preventDefault();
  }

  render() {
    return (
      <form className="Form" onSubmit={this.handleSubmit}>
        <label>
          Name:
          <input name="name" type="text" value={this.state.name} onChange={this.handleChange} />
        </label>

        <label>
          Description:
          <textarea name="description" value={this.state.description} onChange={this.handleChange} />
        </label>

        <label>
          Acepta joe:
          <input name="legal" type="checkbox" value={this.state.legal} onChange={this.handleChange} />
        </label>

        <label for="genderMale">
          <input id="genderMale" type="radio" name="gender" value="male" checked={this.state.gender === 'male'} onChange={this.handleChange} /> Male
        </label>

        <label for="genderFemale">
          <input id="genderFemale" type="radio" name="gender" value="female" checked={this.state.gender === 'female'} onChange={this.handleChange} /> Female
        </label>

        <label for="genderOther">
          <input id="genderOther" type="radio" name="gender" value="other" checked={this.state.gender === 'other'} onChange={this.handleChange} /> Other
        </label>

        <input type="submit" value="Submit" />
      </form>
    );
  }
}

export default Form;
```