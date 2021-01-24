import { Component, OnInit } from '@angular/core';
import { CulturalOfferResponse } from 'src/app/models/cultural-offer-response';
import { CulturalOfferLocation } from 'src/app/models/culutral-offer-location';

@Component({
  selector: 'app-map-item-overview',
  templateUrl: './map-item-overview.component.html',
  styleUrls: ['./map-item-overview.component.scss'],
})
export class MapItemOverviewComponent implements OnInit {
  public offer: CulturalOfferResponse;

  public starCount = 5;
  public starColor = 'primary';
  public starRating = 0;

  constructor() {}

  get slides() {
    return [
      'https://mdbootstrap.com/img/Photos/Slides/img%20(130).jpg',
      'https://gsr.dev/material2-carousel/assets/demo.png',
      'https://gsr.dev/material2-carousel/assets/demo.png',
    ];
  }

  onRatingChanged(rating: number) {
    this.starRating = rating;
  }

  ngOnInit(): void {}
}
