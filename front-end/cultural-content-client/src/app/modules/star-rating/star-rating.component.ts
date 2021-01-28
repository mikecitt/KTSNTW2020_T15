import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewEncapsulation,
} from '@angular/core';

@Component({
  selector: 'mat-star-rating',
  templateUrl: './star-rating.component.html',
  styleUrls: ['./star-rating.component.scss'],
  encapsulation: ViewEncapsulation.Emulated,
})
export class StarRatingComponent implements OnInit {
  @Input('rating') public rating: number;
  @Input('starCount') public starCount: number;
  @Input('color') public color: string;
  @Output() public ratingUpdated = new EventEmitter();

  public snackBarDuration = 2000;
  public ratingArr: number[] = [];

  constructor() {}

  onClick(rating: number) {
    this.ratingUpdated.emit(rating);
    return false;
  }

  showIcon(index: number) {
    if (this.rating >= index + 1) {
      return 'star';
    } else {
      return 'star_border';
    }
  }

  ngOnInit(): void {
    for (let index = 0; index < this.starCount; index++) {
      this.ratingArr.push(index);
    }
  }
}
