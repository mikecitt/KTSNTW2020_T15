import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CulturalOfferSubType } from 'src/app/models/culutral-offer-subType';
import { SubTypeDialogData } from '../subtype-list/subtype-list.component';

@Component({
  selector: 'app-subtype-form',
  templateUrl: './subtype-form.component.html',
  styleUrls: ['./subtype-form.component.scss']
})
export class SubtypeFormComponent implements OnInit {

  form = new FormGroup({
    id: new FormControl(),
    name: new FormControl()
  });

  constructor(
    public dialogRef: MatDialogRef<SubtypeFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: SubTypeDialogData
  ) {
    this.form = new FormGroup({
      id: new FormControl(''),
      name: new FormControl('', [Validators.required, Validators.minLength(4)])
    });
  }

  ngOnInit(): void {
    this.form.setValue({id: this.data.subType.id, name: this.data.subType.name});
  }

  onNoClick(): void{
    this.dialogRef.close(false);
  }

  onSaveClick(): void{
    const newSubType: CulturalOfferSubType = {
      id: this.form.value.id,
      name: this.form.value.name,
      type: {
        id: this.data.subType.type.id,
        name: ''
      }
    };
    // console.log(newSubType)
    this.dialogRef.close(newSubType);
  }
}
