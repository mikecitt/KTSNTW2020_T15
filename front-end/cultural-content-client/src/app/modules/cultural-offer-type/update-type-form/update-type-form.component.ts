import { Component, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { FormControl, FormGroup } from '@angular/forms';
import { CulturalOfferType } from '../../../models/cultural-offer-type';
import { Inject } from '@angular/core';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-update-type-form',
  templateUrl: './update-type-form.component.html',
  styleUrls: ['./update-type-form.component.scss']
})
export class UpdateTypeFormComponent implements OnInit {

  updateForm = new FormGroup({
    id: new FormControl(''),
    name: new FormControl('Initial param', [Validators.required,Validators.minLength(4)])
  })

  constructor(
    public dialogRef: MatDialogRef<UpdateTypeFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: CulturalOfferType
  ) { }

  get name() { return this.updateForm.get('name'); }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
    this.updateForm.setValue(this.data);
  }
  onSaveClick():void{
    this.dialogRef.close(this.updateForm.value);
  }
}
