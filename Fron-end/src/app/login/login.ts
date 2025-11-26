import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../Auth/auth-service';

@Component({
  selector: 'app-login',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './login.html',
  standalone: true,
  styleUrl: './login.css',
})
export class Login implements OnInit {

form!: FormGroup<any>;
private formSubmitAttempt: any;


constructor(private authService: AuthService, private formB: FormBuilder) {
    
}

  ngOnInit(): void {
    this.form = this.formB.group(
      {
        email: ['', Validators.required],
        password:['', Validators.required]
      });
  }

 isFieldInvalid(field: string) {
  return (
    (!this.form?.get(field)?.valid && this.form?.get(field)?.touched) ||
    (this.form?.get(field)?.untouched && this.formSubmitAttempt)
  );
}
login() {
  this.formSubmitAttempt = true;
    if(this.form.valid){
      const {email, password} = this.form.value;
      this.authService.login(email, password).subscribe({
        next: (res)=> {
          const user = res.usuario
          console.log('login completo');
          
        }
      });
}


}
}
