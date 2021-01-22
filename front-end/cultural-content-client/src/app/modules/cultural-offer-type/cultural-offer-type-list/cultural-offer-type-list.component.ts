import { Component, Input, OnInit, Output, OnChanges, ViewChild, AfterViewInit } from '@angular/core';
import { CulturalOfferType} from '../../../models/cultural-offer-type';
import {MatTableDataSource} from '@angular/material/table';
import { EventEmitter } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';

@Component({
  selector: 'app-cultural-offer-type-list',
  templateUrl: './cultural-offer-type-list.component.html',
  styleUrls: ['./cultural-offer-type-list.component.scss']
})
export class CulturalOfferTypeListComponent implements OnInit {

  @Input()
  offerTypes: CulturalOfferType[];

  @Output()
  onCreateTypeEvent = new EventEmitter<any>();
  @Output()
  onDeleteTypeEvent = new EventEmitter<any>();
  @Output()
  onUpdateTypeEvent = new EventEmitter<any>();
  @Output()
  onRowClickEvent = new EventEmitter<any>();

  selectedRowIndex = -1;

  displayedColumns: string[] = ['name', 'options'];

  dataSource: MatTableDataSource<CulturalOfferType>;

  constructor() { }

  ngAfterViewInit(){
  }

  ngOnChanges(): void{
    this.dataSource = new MatTableDataSource<CulturalOfferType>(this.offerTypes);
  }

  ngOnInit(): void {
    this.dataSource = new MatTableDataSource<CulturalOfferType>(this.offerTypes);
  }

  createType(): void{
    this.onCreateTypeEvent.emit("create");
  }

  deleteType(element: any): void{
    this.onDeleteTypeEvent.emit(element.id);
  }

  updateType(element: any): void{
    this.onUpdateTypeEvent.emit(element);
  }

  onRowClick(element: any){
    this.onRowClickEvent.emit(element.id);
  }

}
