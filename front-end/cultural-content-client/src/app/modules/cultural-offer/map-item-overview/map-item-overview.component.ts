import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SnackBarComponent } from 'src/app/core/snack-bar/snack-bar.component';
import { CulturalOfferResponse } from 'src/app/models/cultural-offer-response';
import { CulturalOfferService, UserService } from 'src/app/services';
import { EditCulturalOfferDialogComponent } from '../edit-cultural-offer-dialog/edit-cultural-offer-dialog.component';
import { MapItemComponent } from '../map-item/map-item.component';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-map-item-overview',
  templateUrl: './map-item-overview.component.html',
  styleUrls: ['./map-item-overview.component.scss'],
})
export class MapItemOverviewComponent implements OnInit {
  public offer: CulturalOfferResponse;

  public page: MapItemComponent;
  environment = environment;
  public starCount = 5;
  public starColor = 'primary';
  public starRating = 0;

  constructor(
    public dialog: MatDialog,
    private userService: UserService,
    private service: CulturalOfferService,
    private snackBarComponent: SnackBarComponent
  ) {}

  get slides() {
    return this.offer.images.map((image) => `http`);
  }

  onRatingChanged(rating: number) {
    this.starRating = rating;
  }

  ngOnInit(): void {}

  get isAdmin() {
    return this.userService.getRole() == 'ROLE_ADMIN';
  }

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

  delete() {
    this.service.delete(this.offer.id).subscribe((data) => {
      this.snackBarComponent.openSnackBar(
        'Successfully deleted',
        '',
        'green-snackbar'
      );
      this.page.deleteCulturalOffer(this.offer);
    });
  }
}
