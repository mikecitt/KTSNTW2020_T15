import { HttpClient } from '@angular/common/http';
import {
  Component,
  ViewChild,
  AfterViewInit,
  Input,
  OnInit,
  OnChanges,
  SimpleChanges,
} from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { merge, Observable, of as observableOf } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';
import { CulturalOfferResponse } from 'src/app/models/cultural-offer-response';
import { CulturalOfferService } from 'src/app/services';

@Component({
  selector: 'app-table-view',
  templateUrl: './table-view.component.html',
  styleUrls: ['./table-view.component.scss'],
})
export class TableViewComponent implements AfterViewInit, OnChanges {
  displayedColumns: string[] = ['name', 'location', 'subType'];

  @Input()
  culturalOffers: CulturalOfferResponse[];

  dataSource = new MatTableDataSource<CulturalOfferResponse>();

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private service: CulturalOfferService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.culturalOffers) {
      this.dataSource.data = this.culturalOffers;
    }
  }

  ngAfterViewInit() {
    this.service.getLocations().subscribe((data) => {
      this.dataSource.paginator = this.paginator;
    });
  }
}
