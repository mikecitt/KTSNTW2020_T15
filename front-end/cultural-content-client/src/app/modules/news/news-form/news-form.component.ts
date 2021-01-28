import { Inject } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { News } from 'src/app/models/news';

@Component({
  selector: 'app-news-form',
  templateUrl: './news-form.component.html',
  styleUrls: ['./news-form.component.scss']
})
export class NewsFormComponent implements OnInit {

  fileMultiple = true;
  fileAccept = '.png, .jpeg, .jpg';

  form: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<NewsFormComponent>,
    private formBuilder: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data: NewsFormData
    ){
      this.form = this.formBuilder.group({
        newsText: [
          '',
          Validators.compose([
            Validators.required,
            Validators.minLength(10),
            Validators.maxLength(256),
          ]),
        ],
        images: ['', ]
      });
    }

  ngOnInit(): void {
  }

  update(): void{
    this.dialogRef.close({operation: 'update'});
  }

  add(): void{
    this.dialogRef.close({operation: 'add'});
  }

  cancelAdd(): void{
    this.dialogRef.close({operation: 'cancelAdd'});
  }

  cancelUpdate(): void{
    this.dialogRef.close({operation: 'cancelUpdate'});
  }

  filesChanged(): void{
    const self = this;
    this.data.news.images = [];
    // let files= element.target.files;
    const files = this.form.getRawValue().images;
    Array.from(files).forEach(function(file){
      const reader = new FileReader();
      reader.onloadend = function() {
        self.data.news.images.push(reader.result as string);

      };
      reader.readAsDataURL(file as Blob);

    });
  }

}

export interface NewsFormData {
  type: string;
  news: News;
}
