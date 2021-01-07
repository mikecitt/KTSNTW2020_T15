import { Component, OnInit } from '@angular/core';
import { CulturalOfferType} from '../../model/cultural-offer-type';
import { CulturalOfferSubType} from '../../model/culutral-offer-subType';
import { CulturalOfferTypeService } from '../../service/cultural-offer-type/cultural-offer-type.service';
import { CreateTypeFormComponent } from '../../create-type-form/create-type-form.component';
import { MatDialog } from '@angular/material/dialog';
import { UpdateTypeFormComponent } from 'src/app/update-type-form/update-type-form.component';

@Component({
  selector: 'app-cultural-offer-type-page',
  templateUrl: './cultural-offer-type-page.component.html',
  styleUrls: ['./cultural-offer-type-page.component.scss']
})
export class CulturalOfferTypePageComponent implements OnInit {

  culturalOfferTypes: CulturalOfferType[];
  culturalOfferSubTypes: CulturalOfferSubType[];

  constructor(
    private typeService: CulturalOfferTypeService, public dialog: MatDialog
  ) { }

  loadCulturalOfferTypes(): void{
    this.typeService
        .getAll()
        .subscribe(res => this.culturalOfferTypes = res);
  }

  ngOnInit(): void {
    this.loadCulturalOfferTypes();
  }
  openCreateDialog(event:any){
    const dialogRef = this.dialog.open(CreateTypeFormComponent, {
      width: '300px',
      panelClass : "mat-elevation-z8",
      data: {_id: 1, name: ""}
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result != undefined){
        let req: CulturalOfferType = {_id:1, name: result};
        this.typeService.saveType(req)
            .subscribe((response) => {
              this.loadCulturalOfferTypes();
            },
            (error) => {
              alert(error.error)
            });
      }
    });
  }

  openUpdateDialog(updatedType:any):void {
    const dialogRef = this.dialog.open(UpdateTypeFormComponent, {
      width: '300px',
      panelClass : "mat-elevation-z8",
      data: {_id: updatedType.id, name: updatedType.name}
    });

  }

}
