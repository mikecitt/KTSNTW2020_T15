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

  update(): void{
    this.dialogRef.close({operation: 'update'})
  }

  add(): void{
    this.dialogRef.close({operation: 'add'})
  }

  cancelAdd(): void{
    this.dialogRef.close({operation: 'cancelAdd'})
  }

  cancelUpdate(): void{
    this.dialogRef.close({operation: 'cancelUpdate'})
  }

  filesChanged(element : any): void{
    var self = this;
    let files= element.target.files;
    Array.from(files).forEach(function(file){
      var reader = new FileReader();
      reader.onloadend = function() {
        self.data.news.images.push(reader.result as string);
        
      }
      reader.readAsDataURL(file as Blob);
      
    })
  }

}

export interface NewsFormData {
  type: string,
  news: News
}
