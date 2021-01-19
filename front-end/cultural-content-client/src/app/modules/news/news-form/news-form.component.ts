import { Inject } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { News } from 'src/app/models/news';

@Component({
  selector: 'app-news-form',
  templateUrl: './news-form.component.html',
  styleUrls: ['./news-form.component.scss']
})
export class NewsFormComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<NewsFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: NewsFormData
    ){
      
    }

  ngOnInit(): void {
  }

  add(): void{
    this.dialogRef.close({operation: 'add'})
  }

  cancel(): void{
    this.dialogRef.close({operation: 'cancel'})
  }

}

export interface NewsFormData {
  type: string,
  news: News
}
