import { Inject, Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { CulturalOfferType } from '../model/cultural-offer-type';

@Component({
  selector: 'app-create-type-form',
  templateUrl: './create-type-form.component.html',
  styleUrls: ['./create-type-form.component.scss']
})
export class CreateTypeFormComponent implements OnInit {

  name = new FormControl('');

  constructor(
    public dialogRef: MatDialogRef<CreateTypeFormComponent>,
    // @Inject(MAT_DIALOG_DATA) public data: CulturalOfferType
    ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {

  }
  onSaveClick():void{
    this.dialogRef.close(this.name.value);
  }

}
