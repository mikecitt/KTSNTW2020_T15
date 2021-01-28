import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TypeDialogData, CulturalOfferType } from 'src/app/models/cultural-offer-type';

@Component({
  selector: 'app-type-form',
  templateUrl: './type-form.component.html',
  styleUrls: ['./type-form.component.scss']
})
export class TypeFormComponent implements OnInit {

  form: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<TypeFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: TypeDialogData
  ) {
    this.form = new FormGroup({
      id: new FormControl(''),
      name: new FormControl('', [Validators.required, Validators.minLength(4)])
    });
  }

  ngOnInit(): void {
    this.form.setValue({id: this.data.type.id, name: this.data.type.name});
  }

  onNoClick(): void{
    this.dialogRef.close(false);
  }

  onSaveClick(): void{
    const newType: CulturalOfferType = {
      id: this.form.value.id,
      name: this.form.value.name,
    };
    // console.log(newSubType)
    this.dialogRef.close(newType);
  }

}
