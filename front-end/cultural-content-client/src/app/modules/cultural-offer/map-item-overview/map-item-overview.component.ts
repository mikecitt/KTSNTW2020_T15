import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CulturalOfferResponse } from 'src/app/models/cultural-offer-response';
import { EditCulturalOfferDialogComponent } from '../edit-cultural-offer-dialog/edit-cultural-offer-dialog.component';
import { MapItemComponent } from '../map-item/map-item.component';

@Component({
  selector: 'app-map-item-overview',
  templateUrl: './map-item-overview.component.html',
  styleUrls: ['./map-item-overview.component.scss'],
})
export class MapItemOverviewComponent implements OnInit {
  public offer: CulturalOfferResponse;

  public page: MapItemComponent;

  public starCount = 5;
  public starColor = 'primary';
  public starRating = 0;

  constructor(public dialog: MatDialog) {}

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

  openEditDialog() {
    const dialogRef = this.dialog.open(EditCulturalOfferDialogComponent, {
      width: '350px',
      data: this.offer,
    });

    dialogRef
      .afterClosed()
      .subscribe((result: CulturalOfferResponse | undefined) => {
        if (result !== undefined) {
          this.page.updateCulturalOffer(result);
        }
      });
  }
}
