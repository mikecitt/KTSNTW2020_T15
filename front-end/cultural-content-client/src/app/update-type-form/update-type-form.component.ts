import { Component, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { FormControl, FormGroup } from '@angular/forms';
import { CulturalOfferType } from '../model/cultural-offer-type';
import { Inject } from '@angular/core';


@Component({
  selector: 'app-update-type-form',
  templateUrl: './update-type-form.component.html',
  styleUrls: ['./update-type-form.component.scss']
})
export class UpdateTypeFormComponent implements OnInit {

  updateForm = new FormGroup({
    _id: new FormControl(''),
    name: new FormControl('')
  })

  constructor(
    public dialogRef: MatDialogRef<UpdateTypeFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: CulturalOfferType
  ) { }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
    console.log(this.data);
    this.updateForm.setValue(this.data);
  }
  onSaveClick():void{
    this.dialogRef.close(this.updateForm.value);
  }
}
